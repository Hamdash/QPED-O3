package eu.qped.java.checkers.design.ckjm;

import gr.spinellis.ckjm.AbstractClassVisitor;
import gr.spinellis.ckjm.IClassMetricsContainer;
import gr.spinellis.ckjm.utils.FieldAccess;
import gr.spinellis.ckjm.utils.LoggerHelper;
import gr.spinellis.ckjm.utils.MethodCoupling;
import gr.spinellis.ckjm.utils.MethodInvokation;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import java.util.*;

/**
 * Custom visitor class for the metrics:
 * IC {@link eu.qped.java.checkers.design.helper.SaveMapResults.Metric#IC} and
 * CBM {@link eu.qped.java.checkers.design.helper.SaveMapResults.Metric#CBM}.
 *
 * @author marian (from CKJM-extended tool)
 * @author Jannik Seus (edited)
 */
public class QPEDIcAndCbmClassVisitor extends AbstractClassVisitor {

    private Method[] methods;
    private JavaClass currentClass;
    private ConstantPoolGen parentPool;
    private List<Method[]> parentMethods;
    private Set<MethodInvokation> invocationsFromParents;
    private Set<MethodInvokation> invocationsFromCurrentClass;
    private Set<FieldAccess> parentsReaders;
    private Set<FieldAccess> currentClassSetters;
    private Set<MethodCoupling> methodCouplings;
    private JavaClass[] parents;
    private JavaClass parent;

    /**
     * determines how many inherited methods use a field, that is defined
     * in a new/redefined method.
     */
    private int case1;

    /**
     * determines how many inherited methods call a redefined method
     * and use the return value of the redefined method.
     */
    private int case2;

    /**
     * determines how many inherited methods are called by a redefined method
     * and use a parameter that is defined in the redefined method.
     */
    private int case3;


    QPEDIcAndCbmClassVisitor(IClassMetricsContainer classMap) {
        super(classMap);
    }

    @Override
    protected void visitJavaClass_body(JavaClass jc) {
        case1 = case2 = case3 = 0;
        currentClass = jc;
        try {
            parents = jc.getSuperClasses();
            parentMethods = new ArrayList<Method[]>();
            methods = jc.getMethods();
            invocationsFromParents = new TreeSet<MethodInvokation>();
            invocationsFromCurrentClass = new TreeSet<MethodInvokation>();
            parentsReaders = new TreeSet<FieldAccess>();
            currentClassSetters = new TreeSet<FieldAccess>();
            methodCouplings = new TreeSet<MethodCoupling>();

            for (JavaClass j : parents) {
                parentPool = new ConstantPoolGen(j.getConstantPool());
                parent = j;
                parentMethods.add(j.getMethods());
                for (Method m : j.getMethods()) {
                    m.accept(this);
                }
            }

            for (Method m : methods) {
                if (hasBeenDefinedInParentToo(m)) {
                    investigateMethod(m);
                }
                investigateMethodAndLookForSetters(m);
            }

            countCase1();
            countCase2(); // TODO: remove duplications
            countCase3();
            saveResults();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);

        }
    }

    /**
     * Used to visit methods of parents of investigated class.
     */
    @Override
    public void visitMethod(final Method m) {
        MethodGen mg = new MethodGen(m, getParentClassName(), parentPool);
        if (!mg.isAbstract() && !mg.isNative()) {
            for (InstructionHandle ih = mg.getInstructionList().getStart(); ih != null; ih = ih.getNext()) {
                Instruction i = ih.getInstruction();
                if (!visitInstruction(i)) {

                    i.accept(new org.apache.bcel.generic.EmptyVisitor() {

                        @Override
                        public void visitInvokeInstruction(InvokeInstruction ii) {
                            String methodName = "", className = "";
                            Type[] args = ii.getArgumentTypes(parentPool);
                            methodName = ii.getMethodName(parentPool);
                            className = ii.getClassName(parentPool);

                            MethodInvokation mi = new MethodInvokation(className, methodName, args, getParentClassName(), m.getName(), m.getArgumentTypes());
                            invocationsFromParents.add(mi);

                        }

                        @Override
                        public void visitFieldInstruction(FieldInstruction fi) {
                            if (isGetInstruction(fi)) {
                                FieldAccess fa = new FieldAccess(fi.getFieldName(parentPool), m, parent);
                                parentsReaders.add(fa);
                            }
                        }

                        private boolean isGetInstruction(FieldInstruction fi) {
                            String instr = fi.toString(parentPool.getConstantPool());
                            return instr.startsWith("get");
                        }

                    });
                }
            }
        }
    }

    /**
     * Determines whether a given methodInvocation is a call of a redefined method.
     *
     * @param methodInvocation the given method invocation to be checked
     * @return true when methodInvocation is an invocation of a method that has been redefined in investigated class.
     */
    private boolean callsRedefinedMethod(MethodInvokation methodInvocation) {
        for (Method method : methods) {
            if (isInvocationOfTheMethod(method, methodInvocation)) {
                methodInvocation.setDestClass(mClassName);
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether two given types are equal,
     * i.e. if they have the same length and signature.
     *
     * @param args1 first given type
     * @param args2 second given type
     * @return true if args1 equals args2 on mentioned conditions, else false.
     */
    private boolean compareTypes(Type[] args1, Type[] args2) {
        boolean areEqual = args1.length == args2.length;
        if (areEqual) {
            for (int i = 0; i < args1.length; i++) {
                String methodInvocationArgSignature = args2[i].getSignature();
                if (!args1[i].getSignature().equals(methodInvocationArgSignature)) {
                    areEqual = false;
                    break;
                }
            }
        }
        return areEqual;
    }

    /**
     * for documentation see {@link #case1}.
     */
    private void countCase1() {
        for (FieldAccess fap : parentsReaders) {
            if (!isFieldDefinedInCurrentClass(fap.getFieldName())) {
                for (FieldAccess fac : currentClassSetters) {
                    if (fap.getFieldName().equals(fac.getFieldName())) {
                        MethodCoupling mc = new MethodCoupling(fap.getAccessorClass().getClassName(),
                                fap.getAccessor().getName(),
                                fac.getAccessorClass().getClassName(),
                                fac.getAccessor().getName());
                        if (methodCouplings.add(mc)) {
                            case1++;
                            //System.out.println("!!Case1!" + mc.toString() );
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * for documentation see {@link #case3}.
     */
    private void countCase2() {
        for (MethodInvokation mi : invocationsFromParents) {
            if (invokedByParents(mi)) {
                if (mi.isNotConstructorInvocation()) {
                    if (callsRedefinedMethod(mi)) {
                        MethodCoupling mc = new MethodCoupling(mi.getDestClass(), mi.getDestMethod(),
                                mi.getSrcClass(), mi.getSrcMethod());
                        if (methodCouplings.add(mc)) {
                            //System.out.println( "!!Case2!" + mc.toString() );
                            case2++;
                        }
                    }
                }
            }
        }
    }

    /**
     * for documentation see {@link #case3}.
     */
    private void countCase3() {
        boolean isFromParents = false;
        for (MethodInvokation mi : invocationsFromCurrentClass) {
            if (mi.isNotConstructorInvocation() && !isRedefinedInCurrentClass(mi)) {
                for (int i = 0; i < parentMethods.size(); i++) {
                    Method[] get = parentMethods.get(i);
                    int j = 0;
                    while (j < get.length) {
                        Method m = get[j];
                        isFromParents = isInvocationOfTheMethod(m, mi);
                        if (isFromParents) {
                            mi.setDestClass(parents[i].getClassName());
                            MethodCoupling mc = new MethodCoupling(mi.getDestClass(), mi.getDestMethod(),
                                    mi.getSrcClass(), mi.getSrcMethod());
                            if (methodCouplings.add(mc)) {
                                //System.out.println( "!!Case3!" + mc.toString() );
                                break;
                            }
                        }
                        j++;
                    }
                    if (isFromParents) {
                        break;
                    }
                }
                if (isFromParents) {
                    case3++;
                }
            }
        }
    }

    /**
     * Determines whether two methods are equal,
     * i.e. when they have the same name and the same set of arguments.
     *
     * @return true if m equals pm, else false
     */
    private boolean equalMethods(Method m, Method pm) {
        if (m.getName().equals(pm.getName())) {
            if (compareTypes(m.getArgumentTypes(), m.getArgumentTypes())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Investigates method - a member of the currently investigated class.
     */
    private void investigateMethod(final Method m) {
        MethodGen mg = new MethodGen(m, mClassName, mPoolGen);
        if (!mg.isAbstract() && !mg.isNative()) {
            for (InstructionHandle ih = mg.getInstructionList().getStart(); ih != null; ih = ih.getNext()) {
                Instruction i = ih.getInstruction();
                if (!visitInstruction(i)) {
                    i.accept(new org.apache.bcel.generic.EmptyVisitor() {

                        @Override
                        public void visitInvokeInstruction(InvokeInstruction ii) {
                            String methodName = "";
                            String className = "";
                            Type[] args = ii.getArgumentTypes(mPoolGen);
                            methodName = ii.getMethodName(mPoolGen);
                            className = ii.getClassName(mPoolGen);

                            if (args.length > 0) {
                                MethodInvokation mi = new MethodInvokation(className, methodName, args, mClassName, m.getName(), m.getArgumentTypes());
                                invocationsFromCurrentClass.add(mi);
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * Investigates method (and looks for setters) - a member of the currently investigated class.
     */
    private void investigateMethodAndLookForSetters(final Method m) {
        MethodGen mg = new MethodGen(m, mClassName, mPoolGen);
        if (!mg.isAbstract() && !mg.isNative()) {
            for (InstructionHandle ih = mg.getInstructionList().getStart(); ih != null; ih = ih.getNext()) {
                Instruction i = ih.getInstruction();
                if (!visitInstruction(i)) {
                    i.accept(new org.apache.bcel.generic.EmptyVisitor() {

                        @Override
                        public void visitFieldInstruction(FieldInstruction fi) {

                            if (isSetInstruction(fi)) {
                                FieldAccess fa = new FieldAccess(fi.getFieldName(mPoolGen), m, currentClass);
                                currentClassSetters.add(fa);
                            }
                        }

                        private boolean isSetInstruction(FieldInstruction fi) {
                            String instr = fi.toString(currentClass.getConstantPool());
                            return instr.startsWith("put");
                        }

                    });
                }
            }
        }
    }

    /**
     * Determines whether a given field is defined in the current class.
     *
     * @param fieldName the name of the field to be checked
     * @return true if given field is defined in {@link #currentClass}, else false
     */
    private boolean isFieldDefinedInCurrentClass(String fieldName) {
        for (Field f : currentClass.getFields()) {
            if (f.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether a given method invocation comes from parents.
     *
     * @param methodInvocation the given method invocation
     * @return true if it was invoked by parents, else false.
     */
    private boolean invokedByParents(MethodInvokation methodInvocation) {
        for (JavaClass jc : parents) {
            if (jc.getClassName().equals(methodInvocation.getDestClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * It compares the method's names and the lists of method's arguments.
     * Determines whether a given method invocation comes from method m.
     *
     * @param m                given method
     * @param methodInvocation the given method invocation
     * @return true if it was invoked by method, else false.
     */
    private boolean isInvocationOfTheMethod(Method m, MethodInvokation methodInvocation) {

        if (m.getName().equals(methodInvocation.getDestMethod())) {
            Type[] args = m.getArgumentTypes();
            return compareTypes(args, methodInvocation.getDestMethodArgs());
        }
        return false;
    }

    /**
     * Determines whether method m has already been defined in parent class.
     *
     * @param m the given method
     * @return true if defined in parent, else false.
     */
    private boolean hasBeenDefinedInParentToo(Method m) {

        String name = m.getName();
        if (name.equals("<init>") || name.equals("<clinit>")) {
            return false; //constructors cannot be redefined
        }

        Iterator<Method[]> itr = parentMethods.iterator();
        while (itr.hasNext()) {
            Method[] parentMethods = itr.next();
            for (Method pm : parentMethods) {
                if (equalMethods(m, pm)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines whether method mi is redefined in current class.
     *
     * @param mi the given method invocation
     * @return true if redefined in current class, else false.
     */
    private boolean isRedefinedInCurrentClass(MethodInvokation mi) {
        for (Method m : methods) {
            if (isInvocationOfTheMethod(m, mi)) {
                return true;
            }
        }
        return false;
    }

    /**
     * saves the generated results to {@link #mClassMetrics}
     */
    private void saveResults() {
        int sum = case1 + case2 + case3;
        mClassMetrics.setCbm(sum);
        Set<String> coupledParents = new TreeSet<String>();

        for (MethodCoupling mc : methodCouplings) {
            if (mc.getClassA().equals(mClassName)) {
                coupledParents.add(mc.getClassB());
                if (mc.getClassB().equals(mClassName))
                    LoggerHelper.printError("Both of the involved in MethodCoupling classes are the investigated class!", new RuntimeException());
            } else {
                coupledParents.add(mc.getClassA());
                if (!mc.getClassB().equals(mClassName))
                    LoggerHelper.printError("None  of the involved in MethodCoupling classes is the investigated class!", new RuntimeException());
            }
        }
        mClassMetrics.setIc(coupledParents.size());
    }


    /**
     * Determines whether a single instruction was visited.
     *
     * @param instruction the given instruction
     * @return true if instruction instruction was visited, else false.
     */
    private boolean visitInstruction(Instruction instruction) {
        short opcode = instruction.getOpcode();

        return ((InstructionConstants.INSTRUCTIONS[opcode] != null) /*&&
           !(instruction instanceof ConstantPushInstruction) &&
           !(instruction instanceof ReturnInstruction)*/);
    }

    /**
     * Getter method for parent's class name.
     *
     * @return the parent class name as a String
     */
    private String getParentClassName() {
        return parent.getClassName();
    }

}
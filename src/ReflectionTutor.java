/**
 * Created by AKoscinski on 2016-05-06.
 */
    import java.io.PrintStream;
    import java.lang.annotation.Annotation;
    import java.lang.reflect.Array;
    import java.lang.reflect.Constructor;
    import java.lang.reflect.Field;
    import java.lang.reflect.Method;
    import java.lang.reflect.Modifier;
    import java.lang.reflect.Type;
    import java.util.ArrayList;
    import java.util.List;

    import javax.lang.model.type.TypeVariable;

    import org.junit.Test;

    public class ReflectionTutor{
        final static String introspectClass = "ExampleClass";

        @Test
        public void testReflection() {
            try {
                //load ExampleClass at runtime by name
                Class<?> c = Class.forName(introspectClass);

                //show all constructors (use method showConstructors())
                showConstructors(c);
                System.out.println("---------------------------------------------");
                //list all methods, return types and arguments
                Method[] methods = c.getMethods();
                for(Method m : methods){
                    System.out.println(m.getName() + " return type: " + m.getReturnType().getSimpleName() + " arguments: ");
                    Class<?>[] parameterTypes = m.getParameterTypes();
                    for (Class<?> param : parameterTypes){
                        System.out.println(param.getSimpleName());
                    }
                }
                System.out.println("---------------------------------------------");
                //list all fields and types of the class
                Field[] declaredFields = c.getDeclaredFields();
                for (Field f: declaredFields) {
                    System.out.println(f.getName() + " : " + f.getType().getSimpleName());
                }
                System.out.println("---------------------------------------------");
                //call the printIt() method
                Class[] paramTypes = new Class[]{};
                Method m = c.getMethod("printIt", paramTypes);
                Object[] args = new Object[]{};
                Object obj = c.newInstance();
                m.invoke(  obj, args  );
                System.out.println("---------------------------------------------");

                //call the printItString() method, pass a String param
                Class[] paramTypes2 = new Class[]{String.class};
                Method m2 = c.getMethod("printItString", paramTypes2);
                Object[] args2 = new Object[]{"test"};
                m2.invoke(  obj, args2  );
                System.out.println("---------------------------------------------");

                //call the printItInt() method, pass a int param
                Class[] paramTypes3 = new Class[]{int.class};
                Method m3 = c.getMethod("printItInt", paramTypes3);
                Object[] args3 = new Object[]{4};
                m3.invoke(  obj, args3  );
                System.out.println("---------------------------------------------");

                //call the setCounter() method, pass a int param
                Class[] paramTypes4 = new Class[]{int.class};
                Method m4 = c.getMethod("setCounter", paramTypes3);
                Object[] args4 = new Object[]{5};
                m3.invoke(  obj, args4  );
                System.out.println("---------------------------------------------");

                //call the printCounter() method
                Class[] paramTypes5 = new Class[]{};
                Method m5 = c.getMethod("printCounter", paramTypes5);
                Object[] args5 = new Object[]{};
                m5.invoke(  obj, args5  );
                System.out.println("---------------------------------------------");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void showConstructors(Class clazz) {
            // list of constructors
            Constructor[] constructors = clazz.getConstructors();
            for (Constructor constr:clazz.getConstructors()) {
                StringBuilder sb = new StringBuilder();
                for (Class param: constr.getParameterTypes()) {
                    if (sb.length()>0) sb.append(", ");
                    sb.append(param.getSimpleName());
                }
                sb.insert(0, "constructor: "+constr.getName()+"(");
                sb.append(")");
                System.out.println(sb.toString());
            }
            System.out.println("SuperClass: "+clazz.getSuperclass().getSimpleName());
        }

        @Test
        public void testShowConstructors() {
            showConstructors(java.lang.String.class);
        }

    }


    class ExampleClass {
        public String name;
        private String text;
        private int counter;

        public ExampleClass() {
        }

        public ExampleClass(String text, int counter) {
            super();
            this.text = text;
            this.counter = counter;
        }

        public void printIt(){
            System.out.println("printIt() no param");
        }

        public void printItString(String temp){
            System.out.println("printIt() with param String : " + temp);
        }

        public void printItInt(int temp){
            System.out.println("printIt() with param int : " + temp);
        }

        public void setCounter(int counter){
            this.counter = counter;
            System.out.println("setCounter() set counter to : " + counter);
        }

        public void printCounter(){
            System.out.println("printCounter() : " + this.counter);
        }

    }


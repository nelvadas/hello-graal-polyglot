
package com.oracle.graalvm;

import java.util.function.Function;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;


/**
 * Hello world Polyglot !
 *
 */
public class App 
{

        public static void main(String[] args) {
            
                // A python function 
                String pythonGreetFunctionCode = """
                import polyglot
                from datetime import datetime

                @polyglot.export_value
                def greet(name="World"):
                    print(f"Hello, {name}!")
                    return datetime.now().strftime(f"{name} %d-%m-%Y %H:%M" )
                """;
            
          
                Context context = Context.newBuilder("js", "python","llvm").allowAllAccess(true).build();
                
                //1 Java - Python basic 
                int pyResult = context.eval("python", "[1,2,42,4]").getArrayElement(2).asInt();
                System.out.println(" Python Array[2]  is :"+ pyResult);


                


                //2 - Calling a python function 
                Source source = Source.create("python", pythonGreetFunctionCode);
                //you can reference various sources including files.
                Value pyPart = context.eval(source );
                @SuppressWarnings("unchecked")
                Function<String, String> greetFunction = pyPart.getContext()
                        .getPolyglotBindings()
                        .getMember("greet")
                         .as(Function.class);
                 String message = greetFunction.apply("GraalVM");
                System.out.println(message);
            
            
        }
}

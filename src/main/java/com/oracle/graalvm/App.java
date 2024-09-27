
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
            




                // call a python function 

                String pythonGreetFunctionCode = """
                import polyglot
                from datetime import datetime

                @polyglot.export_value
                def greet(name="World"):
                    print(f"Hello, {name}!")
                    return datetime.now().strftime(f"{name} %d-%m-%Y %H:%M" )
                """;

                Context context = Context.newBuilder().allowAllAccess(true).build();

                Source source = Source.create("python", pythonGreetFunctionCode);
                //you can reference various sources including files.
                Value pyPart = context.eval(source );
                Function<String, String> greetFunction = pyPart.getContext().getPolyglotBindings().getMember("greet").as(Function.class);
                 String message = greetFunction.apply("GraalVM");

                System.out.println(message);
            
        }
}

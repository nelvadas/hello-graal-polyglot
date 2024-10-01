# Getting started with GraalVM Polyglot

This helloworld project introduce you to Graal Polyglot 



## Setup 

Download and install [GraalVM](oracle.com/graalvm) 
```
$ sdk install java 23-graal
$ java version "23" 2024-09-17
Java(TM) SE Runtime Environment Oracle GraalVM 23+37.1 (build 23+37-jvmci-b01)
Java HotSpot(TM) 64-Bit Server VM Oracle GraalVM 23+37.1 (build 23+37-jvmci-b01, mixed mode, sharing)
```

## Add a python code in your Java main class like this.

```java 
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
                Value pyPart = context.eval(source );
                Function<String, String> greetFunction = pyPart.getContext().getPolyglotBindings().getMember("greet").as(Function.class);

                 String message = greetFunction.apply("GraalVM");

                System.out.println(message);

``` 

## Run the Polyglot Application

```sh 
$sdk use java 23-graal

$ mvn clean install
```

 
The application call a python function which print a message and return a String 
```shell 
$  java -jar target/helloworld-1.0-SNAPSHOT-jar-with-dependencies.jar
Python Array[2]  is :42
Hello, GraalVM!
GraalVM 27-09-2024 13:46
```


The second line is printed by the python `print` function




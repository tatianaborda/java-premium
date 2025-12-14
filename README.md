# Java Premium Bootcamp - Código Facilito

This repository contains all the practical exercises and projects developed during the **Java Premium Bootcamp** by Código Facilito, taught by [Tatiana Borda](https://www.linkedin.com/in/tatiana-borda/) (Alien Explorer).

## 🎯 About the Bootcamp

The Java Premium Bootcamp is an asynchronous premium program that combines pre-recorded classes with weekly live mentoring sessions. Students learn at their own pace throughout the week and attend live sessions to resolve doubts and reinforce key concepts through practical exercises.

## 📚 Course Content

### Module 1: Introduction to Java
- Java environment setup (JDK 21 - LTS)
- Variables and data types (primitives vs references)
- Operators (arithmetic, logical, relational)
- Control flow structures (if/else, switch, loops)
- Methods and scope
- Input validation with Scanner
- Java naming conventions (camelCase, PascalCase, UPPER_SNAKE_CASE)

### Module 2: Object-Oriented Programming (OOP) and Arrays
- Classes and objects
- Attributes and methods
- Encapsulation (private, public, getters/setters)
- Inheritance (extends, super)
- Polymorphism (method overriding)
- Abstraction (abstract classes and interfaces)
- Collections framework:
  - ArrayList (dynamic lists)
  - HashMap (key-value pairs)
  - For-each loops and lambdas

### Module 3: Advanced Java and Memory Management
- Packages organization
- Exception handling:
  - Checked vs Unchecked exceptions
  - try-catch-finally blocks
  - throws keyword
  - Custom exceptions
- Date and Time API (java.time)
- Advanced Collections (List, Set, Map)
- Streams and Lambda expressions
- JVM (Java Virtual Machine) architecture
- Memory management:
  - Heap and Stack
  - Young and Old Generation
  - Garbage Collection
  - Memory leaks and OutOfMemoryError prevention
  - StackOverflowError

### Module 4: Build Tools (Maven and Gradle)
- Build automation history and importance
- Maven:
  - Project structure (src/main/java, src/test/java)
  - pom.xml configuration
  - GAV (GroupId, ArtifactId, Version)
  - Lifecycle phases (clean, compile, test, package, install, deploy)
  - Dependency management
- Gradle:
  - build.gradle syntax (Groovy/Kotlin DSL)
  - Gradle Wrapper (gradlew)
  - Build cache and incremental compilation
  - Performance advantages
- Maven vs Gradle comparison
- Industry adoption and use cases

### Module 5: File Management in Java
- File reading and writing:
  - Traditional approach (java.io)
  - Modern approach (java.nio.file)
- Directory manipulation
- Try-with-resources for automatic resource management
- Serialization and Deserialization:
  - Serializable interface
  - ObjectOutputStream and ObjectInputStream
  - transient keyword
- CLASSPATH understanding

### Module 6: Advanced File Management
- Java I/O fundamentals:
  - Streams (byte streams vs character streams)
  - Buffered classes (BufferedReader, BufferedWriter)
  - Reader/Writer vs Streams
- Java NIO (New I/O):
  - Channels and Buffers
  - Selectors for non-blocking I/O
- Properties files:
  - Reading and writing .properties files
  - XML format support
  - ResourceBundle and Locale for internationalization
- java.nio.file API:
  - Path and Files classes
  - File operations (create, copy, move, delete)
  - Directory traversal

### Module 7: Testing in Java
- Testing fundamentals:
  - Types of testing (Unit, Integration, System, Manual)
  - Importance and objectives
- JUnit framework:
  - Annotations (@Test, @BeforeEach, @AfterEach, @BeforeAll, @AfterAll, @Disabled)
  - Assertions
  - Parameterized tests
- Mocking concepts (isolating dependencies)
- SOLID principles:
  - Single Responsibility Principle
  - Open/Closed Principle
  - Liskov Substitution Principle
  - Interface Segregation Principle
  - Dependency Inversion Principle
- Writing clean and testable code

## 🛠️ Main Project: Personal Expense Calculator

Throughout the bootcamp, students progressively build a **Personal Expense Calculator** that evolves with each module:

- **Module 1:** Basic console application with variables, control flow, and Scanner validation
- **Module 2:** Refactored with OOP principles (Movement abstract class, Expense and Income classes)
- **Module 3:** Enhanced with exception handling, date/time management, and streams
- **Module 4:** Configured as a Maven/Gradle project with proper dependency management
- **Module 5:** Added file persistence using serialization
- **Module 6:** Implemented advanced file operations and configuration with .properties files
- **Module 7:** Complete unit test coverage with JUnit

## 🎓 Instructor

**Tatiana Borda** (Alien Explorer)
- Full-stack developer with 5+ years of experience in Java, JavaScript, Solidity, and Rust
- Web3 enthusiast and hackathon participant
- Founder of "Buen Día Builders" organization
- Content creator on YouTube: [AlienExplorer](https://www.youtube.com/@AlienExplorer)

## 🔗 Links

- [LinkedIn](https://www.linkedin.com/in/tatiana-borda/)
- [YouTube Channel](https://www.youtube.com/@AlienExplorer)
- [Código Facilito](https://codigofacilito.com/)

## 📝 Requirements

- Java JDK 21 (LTS version)
- Maven or Gradle
- IntelliJ IDEA (recommended) or any Java IDE
- Git for version control

## 🚀 Getting Started

1. Clone this repository:
```bash
git clone https://github.com/tatianaborda/java-premium.git
```

2. Navigate to the project directory:
```bash
cd java-premium
```

3. If using Maven:
```bash
mvn clean install
mvn exec:java
```

4. If using Gradle:
```bash
./gradlew build
./gradlew run
```

## 📂 Project Structure

```
java-premium/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/codigofacilito/
│   │           ├── model/
│   │           ├── service/
│   │           └── util/
│   └── test/
│       └── java/
├── config.properties
└── pom.xml (or build.gradle)
```

## 📖 Learning Outcomes

By completing this bootcamp, students will be able to:

✅ Write clean, maintainable Java code following industry best practices  
✅ Apply Object-Oriented Programming principles effectively  
✅ Handle exceptions and manage memory efficiently  
✅ Work with modern Java APIs (Collections, Streams, NIO)  
✅ Build projects using Maven or Gradle  
✅ Implement file persistence and configuration management  
✅ Write comprehensive unit tests with JUnit  
✅ Follow SOLID principles for better software design  
✅ Understand JVM internals and performance optimization  

## 🤝 Contributing

This is an educational repository for the Java Premium Bootcamp. If you're a student of the course and want to share improvements or fixes, feel free to open a pull request!

## 📄 License

This project is created for educational purposes as part of the Código Facilito Java Premium Bootcamp.

---

**Made with ❤️ by the Java Premium Bootcamp students**

package com.example.cnc

private fun q(text: String, answer: Int, vararg options: String) = Question(text, options.toList(), answer)

/**
 * Practice questions for every topic: 3 per topic, untimed, unranked.
 * Key format: "<moduleId>/<topic title>"; DSA uses "dsa/<Language>/<topic title>",
 * and topics shared by all languages use a star in place of the language
 * (for example, the Linked List topic).
 * Topic titles must match CurriculumData exactly.
 */
object TopicQuizData {

    fun questionsFor(moduleId: String, track: String?, topicTitle: String): List<Question> =
        if (moduleId == "dsa" && track != null)
            DATA["dsa/$track/$topicTitle"] ?: DATA["dsa/*/$topicTitle"] ?: emptyList()
        else DATA["$moduleId/$topicTitle"] ?: emptyList()

    private val DATA: Map<String, List<Question>> = mapOf(

        // ===================== BASIC =====================
        "basic/General" to listOf(
            q("A compiler…", 1, "Runs code line by line as it is read", "Translates source code into machine code before it runs", "Fixes bugs automatically", "Stores variables"),
            q("Which is a high-level language?", 3, "Machine code", "Binary", "Assembly", "Kotlin"),
            q("What is an algorithm?", 0, "A step-by-step procedure to solve a problem", "A programming language", "A type of variable", "A compiler error")
        ),
        "basic/Sequential Logic / Loops" to listOf(
            q("Which loop is best when you know the number of iterations?", 0, "for", "while", "do-while", "if"),
            q("An infinite loop means…", 2, "It runs exactly once", "It runs zero times", "Its condition never becomes false", "It always causes a compile error"),
            q("How many times does `for (i in 1..3)` run in Kotlin?", 1, "2", "3", "4", "1")
        ),
        "basic/OOPs" to listOf(
            q("An object is…", 2, "A function", "A file", "An instance of a class", "A loop"),
            q("Polymorphism means…", 0, "One interface, many forms", "Hiding data", "Copying objects", "Looping over objects"),
            q("Which Kotlin keyword allows a class to be inherited?", 3, "final", "static", "inherit", "open")
        ),
        "basic/Selection / Conditions" to listOf(
            q("Which statement runs code only when a condition is true?", 1, "for", "if", "return", "break"),
            q("What does `&&` mean?", 2, "OR", "NOT", "AND", "XOR"),
            q("Which Kotlin construct replaces switch?", 0, "when", "case", "select", "match")
        ),
        "basic/Functions" to listOf(
            q("A Kotlin function with no meaningful result returns…", 1, "Null", "Unit", "Zero", "An empty string"),
            q("Variables declared inside a function are…", 3, "Global", "Shared by all functions", "Constants", "Local to that function"),
            q("Passing an argument by value means…", 0, "A copy of the value is passed", "Nothing is passed", "The function is copied", "The variable is deleted")
        ),
        "basic/Variables & Constants" to listOf(
            q("Which Kotlin declaration cannot be reassigned?", 2, "var x = 1", "int x = 1", "val x = 1", "let x = 1"),
            q("Which type stores true or false?", 1, "Int", "Boolean", "String", "Double"),
            q("What is the default type of the literal 3.14 in Kotlin?", 0, "Double", "Float", "Int", "Long")
        ),
        "basic/Data Structures" to listOf(
            q("Which structure has a fixed size and indexed access?", 2, "Stack", "Queue", "Array", "Set"),
            q("A set does not allow…", 0, "Duplicate elements", "Strings", "Numbers", "Iteration"),
            q("Which structure maps keys to values?", 3, "List", "Array", "Set", "Map")
        ),
        "basic/Jump Statements" to listOf(
            q("Which statement exits the nearest loop?", 1, "continue", "break", "skip", "pass"),
            q("`continue` makes the loop…", 0, "Skip to the next iteration", "Stop completely", "Restart from the beginning", "Exit the function"),
            q("Which statement ends a function immediately?", 2, "break", "continue", "return", "loop")
        ),

        // ===================== DSA: language-specific =====================
        "dsa/Python/Built-in Data Structures" to listOf(
            q("Which Python type is ordered and mutable?", 0, "list", "tuple", "frozenset", "str"),
            q("Which built-in has O(1) average membership test?", 3, "list", "tuple", "string", "set"),
            q("`d = {}` creates a…", 1, "set", "dict", "list", "tuple")
        ),
        "dsa/Python/Standard Library Modules" to listOf(
            q("Which gives a double-ended queue?", 2, "heapq", "bisect", "collections.deque", "itertools"),
            q("`collections.Counter` is used to…", 0, "Count hashable items", "Sort lists", "Open files", "Generate random numbers"),
            q("Python's heapq implements a…", 3, "Max-heap", "Stack", "Linked list", "Min-heap")
        ),
        "dsa/Python/User-Defined Structures" to listOf(
            q("Which method runs when a Python object is created?", 1, "__str__", "__init__", "__make__", "__del__"),
            q("What does `self` refer to?", 2, "The class", "The module", "The current instance", "The parent class"),
            q("A stack built on a list typically uses…", 0, "append and pop", "insert(0) and remove", "sort and reverse", "extend and clear")
        ),
        "dsa/Python/Practice" to listOf(
            q("Time complexity of `x in some_list`?", 1, "O(1)", "O(n)", "O(log n)", "O(n log n)"),
            q("Best way to count how often items appear?", 3, "Nested loops only", "Sorting only", "Recursion", "A dict or Counter"),
            q("`list(reversed(a))` returns…", 0, "A new list in reverse order", "The same list", "None", "A set")
        ),

        "dsa/Java/Arrays & Strings" to listOf(
            q("Java Strings are…", 2, "Mutable", "Primitive", "Immutable", "Arrays of ints"),
            q("To build a string efficiently in a loop use…", 0, "StringBuilder", "String +=", "char[] only", "Scanner"),
            q("The length of a Java array is read with…", 1, "length()", "length", "size()", "count")
        ),
        "dsa/Java/Collections Framework" to listOf(
            q("Which List is backed by a resizable array?", 3, "LinkedList", "HashSet", "TreeMap", "ArrayList"),
            q("Which map keeps its keys sorted?", 1, "HashMap", "TreeMap", "LinkedList", "ArrayList"),
            q("Java's PriorityQueue is by default a…", 0, "Min-heap", "Max-heap", "Stack", "Deque")
        ),
        "dsa/Java/Utility Classes" to listOf(
            q("`Arrays.sort(a)` sorts…", 2, "A copy of the list", "Strings only", "The array in place", "Nothing")  ,
            q("`Collections.reverse(list)`…", 0, "Reverses the list in place", "Returns a new list", "Sorts the list", "Shuffles the list"),
            q("Optional is used to…", 3, "Sort values", "Open files", "Create threads", "Represent a value that may be absent")
        ),
        "dsa/Java/User-Defined Structures" to listOf(
            q("Generics such as `Stack<T>` let a class…", 1, "Run faster", "Work with different types safely", "Avoid constructors", "Skip compilation"),
            q("A linked-list Node class typically holds…", 2, "Only an int", "A thread", "Data and a reference to the next node", "A database connection"),
            q("Which keyword refers to the current object?", 0, "this", "self", "me", "current")
        ),
        "dsa/Java/Practice" to listOf(
            q("To compare string contents in Java use…", 1, "==", ".equals()", "=", "!="),
            q("Which collection removes duplicates?", 3, "ArrayList", "LinkedList", "Stack", "HashSet"),
            q("Integer overflow in Java…", 0, "Wraps around silently", "Always throws an exception", "Becomes long automatically", "Is a compile error")
        ),

        "dsa/C++/Arrays & Strings" to listOf(
            q("Compared with C-strings, std::string…", 1, "Is fixed size", "Manages its own memory and size", "Has no length", "Cannot be copied"),
            q("Array indices in C++ start at…", 0, "0", "1", "-1", "Any value"),
            q("Out-of-bounds array access in C++ is…", 3, "Always caught", "A compile error", "Always 0", "Undefined behavior")
        ),
        "dsa/C++/STL Containers" to listOf(
            q("Which STL container is a dynamic array?", 2, "list", "map", "vector", "set"),
            q("Average lookup time in unordered_map?", 0, "O(1)", "O(n)", "O(log n)", "O(n log n)"),
            q("std::priority_queue by default is a…", 1, "Min-heap", "Max-heap", "Stack", "Queue")
        ),
        "dsa/C++/STL Algorithms" to listOf(
            q("std::sort has complexity…", 3, "O(n)", "O(n^2)", "O(log n)", "O(n log n)"),
            q("lower_bound requires the range to be…", 0, "Sorted", "Unique", "Positive", "Non-empty only"),
            q("`std::reverse(v.begin(), v.end())`…", 2, "Sorts", "Copies", "Reverses in place", "Deletes")
        ),
        "dsa/C++/User-Defined Structures" to listOf(
            q("struct vs class defaults?", 1, "No difference at all", "struct members are public by default, class members private", "struct cannot have methods", "class cannot have constructors"),
            q("A template lets you…", 0, "Write code that works for many types", "Skip compilation", "Avoid pointers", "Create only macros"),
            q("Which operator accesses a member through a pointer?", 3, ".", "::", "&", "->")
        ),
        "dsa/C++/Practice" to listOf(
            q("Why use `ios::sync_with_stdio(false)` in contests?", 2, "To sort faster", "To enable threads", "To speed up I/O", "To avoid overflow"),
            q("Which type should hold values up to about 10^18?", 1, "int", "long long", "short", "float"),
            q("Passing a vector by `const &` avoids…", 0, "Copying the vector", "Reading it", "Compiling", "Sorting")
        ),

        // ===================== DSA: shared by all languages =====================
        "dsa/*/Linked List" to listOf(
            q("Accessing the k-th element of a singly linked list takes…", 2, "O(1)", "O(log n)", "O(n)", "O(n^2)"),
            q("To reverse a singly linked list you…", 0, "Flip each node's next pointer", "Sort the nodes", "Delete the head", "Copy it twice"),
            q("A doubly linked list node has…", 3, "Only data", "Only a next link", "Two data fields", "Next and previous links")
        ),
        "dsa/*/Trees" to listOf(
            q("Which traversal visits the root first?", 1, "In-order", "Pre-order", "Post-order", "Reverse order"),
            q("In a BST the left child is…", 2, "Larger than its parent", "Equal to its parent", "Smaller than its parent", "Random"),
            q("A binary heap is usually stored in…", 3, "A linked list", "A hash map", "A stack", "An array")
        ),
        "dsa/*/Graphs" to listOf(
            q("BFS finds shortest paths in…", 0, "Unweighted graphs", "Graphs with negative edges", "Only trees", "Only DAGs"),
            q("Adjacency list space for V vertices and E edges?", 1, "O(V^2)", "O(V + E)", "O(E^2)", "O(log V)"),
            q("A connected graph with no cycles is a…", 2, "Cycle", "Clique", "Tree", "Matrix")
        ),
        "dsa/*/Tries (Prefix Trees)" to listOf(
            q("Searching a word of length L in a trie takes…", 3, "O(n)", "O(n log n)", "O(log n)", "O(L)"),
            q("Each trie node typically stores…", 0, "Links to child characters and an end-of-word flag", "Only integers", "A hash of the word", "A sorted array of all words"),
            q("Tries are great for…", 2, "Matrix math", "Sorting integers", "Autocomplete", "Graph coloring")
        ),

        // ===================== SYSTEM DESIGN =====================
        "system_design/Fundamentals" to listOf(
            q("Scalability is…", 1, "Making the UI pretty", "The ability to handle growing load", "Encrypting data", "Reducing code size"),
            q("Availability is usually measured as…", 0, "Percent of uptime", "Lines of code", "Number of users", "Disk size"),
            q("A single point of failure is…", 3, "A fast server", "A backup", "A database index", "A component whose failure brings the system down")
        ),
        "system_design/Back-of-the-Envelope Estimation" to listOf(
            q("1 million requests per day is roughly how many per second?", 2, "~1", "~120", "~12", "~1,200"),
            q("Which is faster to read from?", 0, "Memory", "Disk", "They are the same", "It depends on the file"),
            q("About how many bytes are in 1 GB?", 1, "10^6", "10^9", "10^12", "10^3")
        ),
        "system_design/Load Balancing" to listOf(
            q("Round robin sends requests…", 3, "Always to the first server", "Only to the least used server", "Based on user location", "To each server in turn"),
            q("Health checks are used to…", 0, "Remove failed servers from rotation", "Encrypt traffic", "Cache pages", "Compress responses"),
            q("An L7 load balancer can route using…", 2, "Only IP addresses", "MAC addresses", "HTTP details like URL paths", "Only port numbers")
        ),
        "system_design/Caching" to listOf(
            q("A cache hit means…", 1, "Data was not found", "The requested data was found in the cache", "The cache is full", "The cache crashed"),
            q("TTL controls…", 3, "Cache size", "Bandwidth", "CPU speed", "How long an entry stays valid"),
            q("A write-through cache…", 0, "Writes to the cache and storage together", "Never writes to storage", "Only reads", "Deletes data")
        ),
        "system_design/Databases & Sharding" to listOf(
            q("Which is a common sharding key?", 2, "Row color", "File size", "User ID", "Server name"),
            q("Read replicas help…", 0, "Scale read traffic", "Scale writes only", "Encrypt data", "Shrink tables"),
            q("A drawback of sharding is…", 1, "No scaling", "Cross-shard queries and joins are harder", "Slower disks", "Less storage")
        ),
        "system_design/Message Queues" to listOf(
            q("Kafka is mainly…", 3, "A relational database", "A web server", "A CSS tool", "A distributed event streaming platform"),
            q("Pub/sub means…", 1, "One-to-one only", "Publishers send messages to topics that subscribers receive", "Pull-only polling", "No message brokers"),
            q("A dead-letter queue stores…", 0, "Messages that could not be processed", "Old logs", "User passwords", "Cache entries")
        ),
        "system_design/API Design" to listOf(
            q("Which HTTP method retrieves data?", 2, "POST", "PUT", "GET", "DELETE"),
            q("Pagination is used to…", 0, "Return large results in chunks", "Encrypt responses", "Sort tables", "Log users in"),
            q("Which status code means a resource was created?", 3, "200", "404", "500", "201")
        ),
        "system_design/CAP Theorem & Consistency" to listOf(
            q("Eventual consistency means…", 1, "Data is always instantly in sync", "Replicas converge to the same value given time", "Data is lost", "There is no replication"),
            q("In CAP, P stands for…", 0, "Partition tolerance", "Performance", "Persistence", "Privacy"),
            q("During a partition, a CP system prefers…", 2, "Availability", "Speed", "Consistency", "Low cost")
        ),
        "system_design/Classic Designs" to listOf(
            q("A URL shortener maps…", 0, "Short codes to long URLs", "Passwords to hashes", "IPs to MACs", "Files to folders"),
            q("To generate unique IDs across servers you can use…", 3, "The same counter on every server", "User names", "Page titles", "Snowflake-style or UUID IDs"),
            q("A news feed is often built with…", 1, "Only static pages", "Fan-out on write or on read", "FTP", "Email only")
        ),
        "system_design/Interview Approach" to listOf(
            q("What is the first step in a design interview?", 2, "Draw boxes immediately", "Pick a database", "Clarify requirements", "Estimate server cost"),
            q("After requirements you typically…", 0, "Sketch a high-level design", "Write full code", "End the interview", "Choose colors"),
            q("Discussing trade-offs shows…", 3, "Indecision", "Wasted time", "Memorized answers", "Engineering judgment")
        ),

        // ===================== ARCHITECTURE =====================
        "architecture/Software Architecture Basics" to listOf(
            q("Software architecture is…", 1, "A coding style guide", "The high-level structure of a system", "A test suite", "A database table"),
            q("A quality attribute is…", 0, "A non-functional property like scalability", "A bug", "A function name", "A variable"),
            q("Separation of concerns means…", 2, "Everything in one class", "Avoiding classes", "Splitting responsibilities into distinct parts", "Using only one file")
        ),
        "architecture/Layered Architecture" to listOf(
            q("The data layer is responsible for…", 3, "Drawing buttons", "Business rules", "Navigation", "Storing and retrieving data"),
            q("In a strict layered design, upper layers call…", 0, "The layers directly below them", "Any layer", "Only the UI", "Nothing"),
            q("Which is NOT a typical layer?", 1, "Presentation", "Compiler", "Business", "Data")
        ),
        "architecture/MVC / MVP / MVVM" to listOf(
            q("In MVC the Controller…", 2, "Stores data", "Draws pixels", "Handles input and updates the model/view", "Defines the database"),
            q("In MVVM the View observes…", 0, "ViewModel state", "The database directly", "The network", "Compiler output"),
            q("In MVP the Presenter…", 3, "Replaces the model", "Only draws the UI", "Stores preferences", "Mediates between View and Model")
        ),
        "architecture/Clean Architecture" to listOf(
            q("Use cases contain…", 1, "UI widgets", "Application-specific business rules", "SQL scripts", "Image assets"),
            q("Entities are…", 0, "Core business objects and rules", "UI screens", "API clients", "Test doubles"),
            q("Frameworks and databases belong to the…", 2, "Innermost layer", "Domain", "Outermost layer", "Entities layer")
        ),
        "architecture/Monolith vs Microservices" to listOf(
            q("A drawback of microservices is…", 3, "A single deploy", "No scaling", "A single language", "Operational and network complexity"),
            q("A monolith is…", 1, "Many small services", "One deployable unit", "A database type", "A cloud region"),
            q("Microservices communicate via…", 0, "Network calls such as HTTP or messaging", "Shared memory only", "Shared variables", "Direct method calls in one process")
        ),
        "architecture/Event-Driven Architecture" to listOf(
            q("An event broker…", 2, "Compiles code", "Hosts the UI", "Routes events between producers and consumers", "Stores only images"),
            q("CQRS separates…", 0, "Commands (writes) from queries (reads)", "Client from server", "Code from data", "UI from API"),
            q("Event sourcing stores…", 3, "Only the latest state", "Passwords", "Screenshots", "A sequence of events as the source of truth")
        ),
        "architecture/Design Patterns" to listOf(
            q("The Strategy pattern lets you…", 1, "Create one instance", "Swap algorithms at runtime", "Wrap legacy code", "Observe events"),
            q("The Builder pattern helps with…", 0, "Constructing complex objects step by step", "Notifications", "A single instance", "Interface conversion"),
            q("A common risk of Singletons is…", 2, "Too many instances", "No state", "Hidden global state and harder testing", "They cannot be created")
        ),
        "architecture/SOLID Principles" to listOf(
            q("Liskov Substitution says subclasses should be…", 3, "Faster", "Smaller", "Private", "Usable wherever the parent is expected"),
            q("Interface Segregation prefers…", 0, "Small, focused interfaces", "One giant interface", "No interfaces", "Only abstract classes"),
            q("Dependency Inversion says to depend on…", 1, "Concrete classes", "Abstractions", "Databases", "Files")
        ),
        "architecture/Domain-Driven Design" to listOf(
            q("A bounded context is…", 2, "A database", "A server", "A boundary where a model and its language apply", "A test"),
            q("Ubiquitous language is…", 0, "Shared vocabulary between developers and domain experts", "A programming language", "A UI theme", "Code comments"),
            q("An aggregate is…", 3, "A SQL join", "A button group", "A log file", "A cluster of objects treated as a unit")
        ),
        "architecture/Documenting Architecture" to listOf(
            q("ADR stands for…", 1, "Application Data Record", "Architecture Decision Record", "Automated Deploy Release", "API Design Rule"),
            q("The C4 model's levels are…", 0, "Context, Containers, Components, Code", "Class, Constructor, Compiler, Cache", "Core, Cloud, CPU, Cache", "Client, Controller, Cookie, CDN"),
            q("Why document decisions?", 2, "To pad the repo", "To avoid testing", "To keep the reasoning for future teammates", "Because tools require it")
        ),

        // ===================== CLOUD =====================
        "cloud/Cloud Basics" to listOf(
            q("Pay-as-you-go means…", 0, "You pay for what you use", "A fixed yearly fee", "Free forever", "You pay upfront for hardware"),
            q("A hybrid cloud combines…", 3, "Two public clouds only", "Two laptops", "Two databases", "Private and public cloud resources"),
            q("PaaS provides…", 1, "Raw hardware", "A platform to run apps without managing the OS", "Only email", "Only storage")
        ),
        "cloud/Compute" to listOf(
            q("Which is serverless compute?", 2, "A dedicated server", "A VM you patch yourself", "AWS Lambda or Cloud Functions", "A physical rack"),
            q("Vertical scaling means…", 0, "Giving one machine more CPU or RAM", "Adding more machines", "Deleting data", "Moving regions"),
            q("Spot or preemptible instances are…", 3, "Always free", "More expensive", "Guaranteed forever", "Cheaper but can be interrupted")
        ),
        "cloud/Storage" to listOf(
            q("Block storage is like…", 1, "A website", "A virtual disk attached to a VM", "An email inbox", "A cache only"),
            q("Object storage keeps data as…", 0, "Objects with metadata in buckets", "Rows and columns", "Memory pages", "Sockets"),
            q("Cold storage is for…", 2, "Frequently accessed data", "Live databases", "Rarely accessed archives", "CPU caches")
        ),
        "cloud/Cloud Databases" to listOf(
            q("A managed database means the provider handles…", 3, "Your queries", "Your UI", "Your app code", "Patching, backups and maintenance"),
            q("DynamoDB and Firestore are…", 0, "NoSQL databases", "Relational only", "Message queues", "Load balancers"),
            q("A data warehouse is for…", 1, "Serving web pages", "Analytics on large datasets", "Caching", "Authentication")
        ),
        "cloud/Cloud Networking" to listOf(
            q("A VPC is…", 2, "Virus protection", "A video codec", "A private, isolated virtual network", "A billing tool"),
            q("A subnet is…", 0, "A segment of a network's IP range", "A server", "A firewall rule", "A DNS record"),
            q("A security group acts like…", 3, "A load balancer", "A database", "An email filter", "A virtual firewall for instances")
        ),
        "cloud/Identity & Security" to listOf(
            q("Least privilege means…", 1, "Everyone is admin", "Grant only the permissions needed", "No permissions at all", "Share passwords"),
            q("MFA adds…", 0, "A second verification factor", "Faster login", "Free storage", "More RAM"),
            q("Encryption at rest protects…", 2, "Data in transit only", "Network packets", "Stored data", "The CPU cache")
        ),
        "cloud/Containers & Kubernetes" to listOf(
            q("A Docker image is…", 3, "A running process", "A photo", "A VM disk only", "A read-only template for creating containers"),
            q("A Kubernetes pod is…", 1, "A cluster", "The smallest deployable unit, with one or more containers", "A VM", "A database"),
            q("Docker Hub is…", 0, "A registry for container images", "A text editor", "A VM host", "A CI tool only")
        ),
        "cloud/CI/CD & DevOps" to listOf(
            q("Continuous Integration means…", 2, "Deploying monthly", "Never testing", "Frequently merging code and running automated builds and tests", "Writing docs"),
            q("Terraform is used for…", 0, "Infrastructure as code", "UI design", "Unit tests", "Chat"),
            q("A rollback…", 1, "Deletes the repo", "Returns to a previous working version", "Speeds up the CPU", "Changes the language")
        ),
        "cloud/Monitoring & Cost" to listOf(
            q("Metrics, logs and traces are…", 3, "Billing types", "Security keys", "Storage tiers", "Observability signals"),
            q("Budget alerts help…", 0, "Avoid surprise bills", "Increase quotas", "Encrypt data", "Deploy faster"),
            q("Right-sizing means…", 1, "Always using the largest instance", "Matching resources to actual needs", "Deleting backups", "Using one region")
        ),
        "cloud/Major Providers" to listOf(
            q("AWS is run by…", 2, "Google", "Microsoft", "Amazon", "Oracle"),
            q("Azure is from…", 0, "Microsoft", "Amazon", "Google", "IBM"),
            q("GCP stands for…", 3, "Global Cloud Platform", "General Compute Process", "Google Cloud Program", "Google Cloud Platform")
        ),

        // ===================== COMPUTER NETWORK =====================
        "networks/Network Basics" to listOf(
            q("A LAN covers…", 1, "A whole country", "A small area like a home or office", "The whole internet", "Outer space"),
            q("A router connects…", 0, "Different networks", "Only keyboards", "Only printers", "Nothing"),
            q("Bandwidth is…", 2, "Cable length", "Signal color", "The maximum data transfer rate", "Packet size")
        ),
        "networks/OSI & TCP/IP Models" to listOf(
            q("How many layers does the OSI model have?", 3, "4", "5", "6", "7"),
            q("Which OSI layer is closest to the user?", 0, "Application", "Physical", "Transport", "Network"),
            q("The TCP/IP transport layer holds…", 1, "IP and ICMP", "TCP and UDP", "HTTP", "Ethernet")
        ),
        "networks/IP Addressing & Subnetting" to listOf(
            q("How long is an IPv6 address?", 2, "32 bits", "64 bits", "128 bits", "256 bits"),
            q("The mask 255.255.255.0 is written as…", 0, "/24", "/16", "/8", "/32"),
            q("Which is a private IP address?", 3, "8.8.8.8", "1.1.1.1", "142.250.0.1", "192.168.1.1")
        ),
        "networks/TCP vs UDP" to listOf(
            q("Which is often better for live video streaming?", 1, "TCP always", "UDP", "FTP", "SMTP"),
            q("TCP ensures…", 0, "Reliable, ordered delivery", "No retransmission", "The fastest delivery always", "Broadcast"),
            q("The UDP header is…", 2, "Larger than TCP's", "Encrypted", "Smaller than TCP's", "Absent")
        ),
        "networks/DNS" to listOf(
            q("Which record maps a name to an IPv4 address?", 3, "MX", "CNAME", "TXT", "A"),
            q("Which record points to mail servers?", 0, "MX", "A", "NS", "PTR"),
            q("DNS caching…", 1, "Slows lookups down", "Reduces repeated lookups", "Encrypts traffic", "Deletes domains")
        ),
        "networks/HTTP & HTTPS" to listOf(
            q("HTTPS uses…", 2, "FTP", "SSH only", "TLS", "UDP only"),
            q("HTTP status 301 means…", 0, "Moved permanently", "Not found", "Server error", "OK"),
            q("HTTP is…", 3, "Stateful", "Connection-only", "A database", "Stateless")
        ),
        "networks/Routing & Switching" to listOf(
            q("A switch forwards frames using…", 1, "IP addresses", "MAC addresses", "URLs", "Ports"),
            q("OSPF is a…", 0, "Routing protocol", "Web server", "Database", "Email format"),
            q("A default gateway is…", 2, "A DNS server", "A printer", "The router used for traffic leaving the local network", "A cable")
        ),
        "networks/Network Security" to listOf(
            q("A firewall…", 3, "Speeds up Wi-Fi", "Stores files", "Compiles code", "Filters traffic by rules"),
            q("A VPN…", 0, "Creates an encrypted tunnel", "Cleans viruses", "Hosts websites", "Assigns IPs"),
            q("A DDoS attack tries to…", 1, "Steal passwords quietly", "Overwhelm a service with traffic", "Encrypt files", "Fix bugs")
        ),
        "networks/Sockets Programming" to listOf(
            q("A socket endpoint is defined by…", 2, "A MAC and a name", "A file and a path", "An IP address and a port", "A URL only"),
            q("A server calls listen() to…", 0, "Wait for incoming connections", "Send data", "Close the socket", "Resolve DNS"),
            q("Port numbers go up to…", 3, "255", "1023", "32767", "65535")
        ),
        "networks/Wireless & Mobile Networks" to listOf(
            q("Which is the strongest Wi-Fi security of these?", 1, "WEP", "WPA3", "Open network", "WPA"),
            q("Bluetooth is for…", 0, "Short-range wireless communication", "Long-haul fiber", "Wired LANs", "DNS"),
            q("5G compared with 4G generally offers…", 2, "Lower speed", "Shorter range only", "Higher speed and lower latency", "No change")
        ),

        // ===================== OPERATING SYSTEM =====================
        "os/OS Basics" to listOf(
            q("The kernel is…", 1, "An app", "The core of the OS that manages hardware and processes", "A compiler", "A browser"),
            q("A system call is…", 0, "How user programs request OS services", "A phone call", "A network packet", "A UI event"),
            q("Which of these is an operating system?", 3, "Chrome", "Java", "Kotlin", "Linux")
        ),
        "os/Processes" to listOf(
            q("A process in the 'ready' state is…", 2, "Running", "Terminated", "Waiting for the CPU", "Blocked on I/O"),
            q("The PCB stores…", 0, "Process information like state and registers", "Only files", "Passwords", "Network routes"),
            q("fork() creates…", 1, "A thread", "A child process", "A file", "A socket")
        ),
        "os/Threads & Concurrency" to listOf(
            q("A race condition occurs when…", 3, "Threads finish quickly", "The CPU is fast", "Memory is full", "The outcome depends on the timing of unsynchronized access"),
            q("A lock or synchronized block is used to…", 0, "Protect shared data", "Speed up loops", "Print text", "Open files"),
            q("Threads in one process share…", 2, "Registers", "Stacks", "Heap memory", "Nothing")
        ),
        "os/CPU Scheduling" to listOf(
            q("FCFS means…", 1, "Fastest CPU First", "First Come First Served", "First Cache First Search", "Final Cycle First Stop"),
            q("Preemptive scheduling can…", 0, "Interrupt a running process", "Never switch processes", "Skip I/O", "Remove memory"),
            q("Turnaround time is…", 3, "Waiting time only", "CPU burst only", "I/O time", "Completion time minus arrival time")
        ),
        "os/Deadlocks" to listOf(
            q("Deadlock prevention works by breaking…", 2, "The disk", "The UI", "One of the necessary conditions", "The kernel"),
            q("The Banker's algorithm is for…", 0, "Deadlock avoidance", "Memory paging", "Disk scheduling", "Encryption"),
            q("In a deadlock, processes…", 1, "Finish quickly", "Wait forever for each other", "Use no memory", "Run faster")
        ),
        "os/Memory Management" to listOf(
            q("Paging divides memory into…", 3, "Variable segments", "Files", "Threads", "Fixed-size pages and frames"),
            q("A page table maps…", 0, "Virtual pages to physical frames", "Files to folders", "IPs to MACs", "Threads to cores"),
            q("External fragmentation is…", 2, "Wasted space inside a block", "A hardware fault", "Free memory split into small unusable pieces", "A cache miss")
        ),
        "os/Virtual Memory" to listOf(
            q("Virtual memory lets programs use…", 1, "Only RAM", "More address space than physical RAM", "No memory", "Only cache"),
            q("FIFO page replacement evicts…", 0, "The oldest loaded page", "The newest page", "The largest page", "A random file"),
            q("The TLB is…", 2, "A disk", "A thread list", "A cache for page table entries", "A network device")
        ),
        "os/File Systems" to listOf(
            q("An inode stores…", 3, "Only the file name", "Directory colors", "User passwords", "File metadata and block pointers"),
            q("Journaling helps…", 0, "Recover after crashes", "Speed up CPUs", "Encrypt files", "Compress video"),
            q("Which of these is a file system?", 1, "HTTP", "ext4", "TCP", "SQL")
        ),
        "os/I/O & Device Management" to listOf(
            q("An interrupt…", 2, "Is a bug", "Is a file type", "Signals the CPU that a device needs attention", "Closes the app"),
            q("DMA allows…", 0, "Devices to transfer data to memory without the CPU copying each byte", "The CPU to sleep", "Network routing", "Encryption"),
            q("A device driver…", 3, "Is a user app", "Is a file", "Is a font", "Lets the OS communicate with hardware")
        ),
        "os/Linux & Android Internals" to listOf(
            q("Android is built on the…", 1, "Windows kernel", "Linux kernel", "macOS kernel", "DOS"),
            q("The `ls` command lists…", 0, "Directory contents", "Processes", "Users", "Memory"),
            q("Android apps run in…", 2, "No sandbox", "The kernel", "A sandboxed process per app", "One shared process")
        ),

        // ===================== DBMS =====================
        "dbms/DBMS Basics" to listOf(
            q("DBMS stands for…", 3, "Data Backup Management Script", "Digital Base Memory System", "Database Machine Service", "Database Management System"),
            q("A schema is…", 0, "The structure or design of a database", "A single row", "A backup", "A query result"),
            q("Compared with plain files, a DBMS gives…", 1, "Less security", "Better integrity and concurrent access", "No queries", "Only text")
        ),
        "dbms/ER Modeling" to listOf(
            q("An entity is…", 2, "A column", "A query", "A real-world object stored in the database", "A server"),
            q("Which is a one-to-many relationship?", 0, "One customer, many orders", "One person, one passport", "Many students, many courses", "None of these"),
            q("A weak entity…", 3, "Has no data", "Always has its own primary key", "Is deleted", "Depends on another entity for identification")
        ),
        "dbms/Relational Model" to listOf(
            q("A tuple is…", 1, "A column", "A row", "A table", "A key"),
            q("NULL means…", 0, "An unknown or missing value", "Zero", "An empty string", "False"),
            q("A candidate key is…", 2, "A foreign key", "A duplicate", "A minimal set of attributes that uniquely identifies rows", "A view")
        ),
        "dbms/SQL Fundamentals" to listOf(
            q("Which command adds a row?", 3, "SELECT", "UPDATE", "DELETE", "INSERT"),
            q("WHERE is used to…", 0, "Filter rows", "Sort rows", "Group rows", "Create tables"),
            q("`ORDER BY name DESC` sorts…", 1, "Ascending", "Descending", "Randomly", "Not at all")
        ),
        "dbms/Joins & Subqueries" to listOf(
            q("LEFT JOIN keeps all rows from…", 2, "The right table", "Neither table", "The left table", "Only matches"),
            q("A subquery is…", 0, "A query inside another query", "A table", "A backup", "An index"),
            q("CROSS JOIN produces…", 3, "Only matches", "One row", "Nothing", "Every combination of rows")
        ),
        "dbms/Normalization" to listOf(
            q("2NF removes…", 1, "Duplicate rows", "Partial dependencies", "Primary keys", "Tables"),
            q("3NF removes…", 0, "Transitive dependencies", "Primary keys", "Tables", "Columns"),
            q("Denormalization is done to…", 2, "Reduce data", "Delete keys", "Improve read performance", "Add errors")
        ),
        "dbms/Transactions & ACID" to listOf(
            q("Atomicity means…", 3, "Speed", "Security", "Size", "All operations succeed or none do"),
            q("Durability means…", 0, "Committed data survives crashes", "Data is deleted", "Data is faster", "Data is encrypted"),
            q("A dirty read is…", 1, "Reading deleted tables", "Reading uncommitted data from another transaction", "Reading logs", "A backup")
        ),
        "dbms/Indexing" to listOf(
            q("A B-tree index supports…", 2, "Only equality", "Nothing", "Fast lookups and range queries", "Only text"),
            q("Too many indexes can…", 0, "Slow down writes and use extra space", "Speed up all writes", "Remove rows", "Fix bugs"),
            q("A clustered index…", 3, "Is a cache", "Is a backup", "Is a view", "Determines the physical order of rows")
        ),
        "dbms/NoSQL" to listOf(
            q("Redis is mainly a…", 1, "Relational database", "Key-value store", "Graph database", "Data warehouse"),
            q("Neo4j is a…", 0, "Graph database", "Document database", "Column store", "Cache"),
            q("NoSQL databases often favor…", 2, "Strict joins", "Fixed schemas only", "Horizontal scaling and flexible schemas", "No data")
        ),
        "dbms/Room & SQLite on Android" to listOf(
            q("Room is…", 3, "A UI library", "A network client", "An image loader", "An abstraction layer over SQLite"),
            q("In Room a table is defined by an…", 0, "@Entity class", "@Activity", "@Layout", "@Service"),
            q("Queries in Room are declared in a…", 1, "@Insert only", "@Dao interface", "@Manifest", "@Theme")
        ),

        // ===================== COMPETITIVE =====================
        "competitive/Getting Started" to listOf(
            q("Codeforces is…", 2, "A text editor", "A cloud provider", "A competitive programming platform", "A compiler"),
            q("Most contests judge solutions by…", 0, "Correct output within time and memory limits", "Code length", "Comments", "Variable names"),
            q("The verdict TLE means…", 3, "Wrong answer", "Compile error", "Runtime error", "Time limit exceeded")
        ),
        "competitive/Fast I/O & Templates" to listOf(
            q("For fast bulk input in Python use…", 1, "input() in loops only", "sys.stdin", "print", "eval"),
            q("In C++, `cin.tie(0)` helps…", 0, "Speed up input", "Sort", "Allocate memory", "Avoid overflow"),
            q("A contest template is…", 2, "A bug", "A judge", "Pre-written code reused across problems", "A language")
        ),
        "competitive/Complexity Analysis" to listOf(
            q("O(n log n) is better than…", 3, "O(n)", "O(1)", "O(log n)", "O(n^2)"),
            q("Two nested loops over n each give…", 0, "O(n^2)", "O(n)", "O(log n)", "O(1)"),
            q("Space complexity measures…", 1, "Time", "Memory used", "Lines of code", "Variable names")
        ),
        "competitive/Sorting & Searching" to listOf(
            q("Sliding window is useful for…", 2, "Graphs only", "Tree depth", "Subarray and substring problems", "Prime checks"),
            q("Binary search on the answer works if…", 0, "The check function is monotonic", "The array is random", "n is tiny", "There are no loops"),
            q("Counting sort works well when…", 3, "Values are strings", "The range is huge", "Data is already sorted", "The value range is small")
        ),
        "competitive/Greedy Algorithms" to listOf(
            q("Activity selection picks…", 1, "The longest activity", "The earliest-finishing compatible activity", "A random activity", "The latest start"),
            q("Greedy can fail when…", 0, "Local choices do not lead to a global optimum", "n is small", "Data is sorted", "It never fails"),
            q("Huffman coding is a…", 2, "DP problem", "Backtracking problem", "Greedy algorithm", "Brute force")
        ),
        "competitive/Dynamic Programming" to listOf(
            q("Memoization is…", 3, "Sorting", "Greedy choice", "Random search", "Caching results of subproblems"),
            q("Fibonacci with DP takes…", 0, "O(n)", "O(2^n)", "O(n^2)", "O(n!)"),
            q("0/1 knapsack states are usually…", 1, "Only weight", "Item index and remaining capacity", "Only value", "Nothing")
        ),
        "competitive/Graph Algorithms" to listOf(
            q("DSU supports…", 2, "Sorting", "Hashing", "Union and find on sets", "Matrix multiplication"),
            q("Kruskal's algorithm finds…", 0, "A minimum spanning tree", "A shortest path", "A topological order", "A max flow"),
            q("Topological sort applies to…", 3, "Any graph", "Undirected graphs", "Graphs with cycles", "Directed acyclic graphs")
        ),
        "competitive/Number Theory" to listOf(
            q("gcd(12, 18) is…", 1, "3", "6", "9", "12"),
            q("A prime number has exactly…", 0, "Two divisors", "One divisor", "Three divisors", "Zero divisors"),
            q("Modular exponentiation is done efficiently by…", 2, "Repeated addition", "Looping n times", "Binary exponentiation", "Sorting")
        ),
        "competitive/Bit Manipulation" to listOf(
            q("`x << 1` equals…", 3, "x / 2", "x - 1", "x + 1", "x * 2"),
            q("`x & 1` checks…", 0, "Whether x is odd", "Whether x is prime", "The sign of x", "Whether x is zero"),
            q("The XOR of two equal numbers is…", 1, "1", "0", "The number itself", "-1")
        ),
        "competitive/Contest Strategy & Platforms" to listOf(
            q("If you are stuck on a problem, a good move is to…", 2, "Quit the contest", "Copy others", "Move on and come back later", "Rewrite everything"),
            q("Reading all problems first helps you…", 0, "Pick the easiest ones to solve first", "Waste time", "Break the rules", "Slow down"),
            q("Which is a coding practice platform?", 3, "Figma", "Slack", "Canva", "LeetCode")
        )
    )
}

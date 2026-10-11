package com.example.cnc

import java.time.LocalDate
import java.time.temporal.WeekFields
import kotlin.random.Random

data class Question(val text: String, val options: List<String>, val answer: Int)

data class Profile(val name: String = "", val displayName: String = "", val email: String = "") {
    /** Name shown on leaderboards: display name, else real name, else "You". */
    val label get() = displayName.ifBlank { name }.ifBlank { "You" }
}

data class Attempt(val moduleId: String, val week: String, val score: Int, val timeSec: Int)

/** q(text, indexOfCorrectOption, option0, option1, option2, option3) */
private fun q(text: String, answer: Int, vararg options: String) = Question(text, options.toList(), answer)

object ContestData {
    const val SECONDS_PER_QUESTION = 60
    const val QUESTIONS_PER_CONTEST = 10

    /** 100 points per correct answer + up to 60 bonus points for speed. Wrong/timeout = 0. */
    fun scoreFor(correct: Boolean, secs: Int) =
        if (correct) 100 + maxOf(0, SECONDS_PER_QUESTION - secs) else 0

    /** ISO week key such as "2026-W41". A new key = a new weekly contest. */
    fun weekKey(date: LocalDate = LocalDate.now()): String {
        val f = WeekFields.ISO
        return "%d-W%02d".format(date.get(f.weekBasedYear()), date.get(f.weekOfWeekBasedYear()))
    }

    val contestModuleIds: List<String> get() = BANK.keys.toList()

    /** The 10 questions for this module this week: same for everyone, different every week. */
    fun questionsFor(moduleId: String): List<Question> {
        val all = BANK[moduleId].orEmpty()
        val rng = Random((weekKey() + moduleId).hashCode())
        return all.shuffled(rng).take(QUESTIONS_PER_CONTEST)
    }

    // ---------------------------------------------------------------------------------------
    // QUESTION BANK: 12 per module (10 are picked each week). Add more any time.
    // ---------------------------------------------------------------------------------------
    private val BANK: Map<String, List<Question>> = mapOf(

        "basic" to listOf(
            q("Which loop is guaranteed to run its body at least once?", 2, "for", "while", "do-while", "foreach"),
            q("What does `continue` do inside a loop?", 0, "Skips the rest of the current iteration", "Exits the loop", "Ends the function", "Restarts the program"),
            q("Which Kotlin keyword declares a read-only variable?", 1, "var", "val", "let", "dim"),
            q("Which OOP concept lets a subclass reuse a parent class's members?", 3, "Encapsulation", "Polymorphism", "Abstraction", "Inheritance"),
            q("What is 7 / 2 when both are Int values in Kotlin?", 0, "3", "3.5", "4", "2"),
            q("What type of value does the expression a > b produce?", 2, "Int", "String", "Boolean", "Char"),
            q("Which statement immediately exits a function and can hand back a value?", 1, "break", "return", "continue", "skip"),
            q("A function parameter is…", 0, "A variable in the function definition that receives input", "The value a function returns", "A loop counter", "A comment"),
            q("How many times does a loop over `0 until 5` run its body?", 2, "4", "6", "5", "Infinite"),
            q("Which data structure stores values by unique keys?", 1, "Array", "Map", "Stack", "Queue"),
            q("What makes a recursive function stop calling itself?", 3, "A loop", "A global variable", "A return type", "A base case"),
            q("Encapsulation means…", 1, "Converting types automatically", "Bundling data with the methods that use it and hiding internals", "Running code in parallel", "Compiling code to machine code")
        ),

        "dsa" to listOf(
            q("Average-case lookup time in a hash map?", 0, "O(1)", "O(n)", "O(log n)", "O(n^2)"),
            q("Which data structure is FIFO?", 2, "Stack", "Heap", "Queue", "Tree"),
            q("Time complexity of binary search?", 1, "O(n)", "O(log n)", "O(n log n)", "O(1)"),
            q("In-order traversal of a binary search tree visits nodes in…", 3, "Reverse order", "Random order", "Level order", "Ascending order"),
            q("Which graph traversal typically uses a queue?", 0, "BFS", "DFS", "In-order", "Pre-order"),
            q("Inserting at the head of a singly linked list takes…", 2, "O(n)", "O(log n)", "O(1)", "O(n^2)"),
            q("Which sorting algorithm guarantees O(n log n) in the worst case?", 1, "Quick sort", "Merge sort", "Bubble sort", "Insertion sort"),
            q("Which Python built-in type is immutable?", 3, "list", "dict", "set", "tuple"),
            q("A trie (prefix tree) is mainly used for…", 0, "Prefix-based string search", "Sorting numbers", "Matrix multiplication", "Graph coloring"),
            q("The root of a min-heap holds the…", 2, "Largest element", "Median element", "Smallest element", "Newest element"),
            q("Which structure is the natural fit for checking balanced parentheses?", 1, "Queue", "Stack", "Heap", "Trie"),
            q("Dijkstra's algorithm requires edge weights to be…", 0, "Non-negative", "Integers only", "Unique", "Negative")
        ),

        "system_design" to listOf(
            q("What does a load balancer do?", 2, "Stores user files", "Compiles code", "Distributes incoming traffic across servers", "Encrypts the database"),
            q("During a network partition, the CAP theorem forces a trade-off between…", 1, "Speed and cost", "Consistency and availability", "Security and privacy", "Latency and throughput"),
            q("Which cache eviction policy removes the least recently used item?", 0, "LRU", "MRU", "Random", "LIFO"),
            q("Horizontal scaling means…", 3, "Adding more RAM to one server", "Using a faster CPU", "Compressing data", "Adding more machines"),
            q("A CDN mainly…", 1, "Runs your database", "Serves content from edge locations close to users", "Encrypts passwords", "Schedules batch jobs"),
            q("Sharding a database means…", 2, "Backing it up nightly", "Encrypting each row", "Splitting data across multiple databases", "Adding indexes"),
            q("A message queue helps by…", 0, "Decoupling producers from consumers", "Making queries faster", "Replacing the database", "Removing the need for APIs"),
            q("Which HTTP status code signals 'too many requests' (rate limited)?", 3, "200", "404", "500", "429"),
            q("Consistent hashing is used to…", 1, "Encrypt data", "Minimize data movement when nodes are added or removed", "Compress traffic", "Sort keys alphabetically"),
            q("The main benefit of database replication is…", 2, "Smaller disk usage", "Always faster writes", "Higher availability and read scalability", "No need for backups"),
            q("An idempotent operation is one where…", 0, "Repeating it has the same effect as doing it once", "It can never fail", "It runs asynchronously", "It needs no authentication"),
            q("Latency is…", 3, "Requests handled per second", "Storage size", "Number of servers", "Time taken to handle a single request")
        ),

        "architecture" to listOf(
            q("What does the S in SOLID stand for?", 0, "Single Responsibility", "Simple Syntax", "Scalable Systems", "Static Storage"),
            q("In MVVM, the ViewModel…", 2, "Draws the UI", "Defines the database schema", "Exposes UI state and logic to the View", "Handles only networking"),
            q("Which pattern ensures a class has only one instance?", 1, "Factory", "Singleton", "Builder", "Adapter"),
            q("In Clean Architecture, the dependency rule says dependencies point…", 3, "Outward to frameworks", "Randomly", "Down to the database", "Inward toward business rules"),
            q("A microservices architecture…", 0, "Splits an app into independently deployable services", "Runs everything in one process", "Avoids using networks", "Requires a single database table"),
            q("The Observer pattern is used when…", 2, "Creating objects", "Converting interfaces", "Many objects must be notified of another object's state change", "Hiding a subsystem"),
            q("The Open/Closed principle says classes should be…", 1, "Open for modification, closed for use", "Open for extension, closed for modification", "Always final", "Always abstract"),
            q("The Factory pattern…", 0, "Creates objects without exposing the exact class to create", "Notifies observers", "Stores a single instance", "Wraps legacy code"),
            q("In event-driven architecture, components communicate mainly via…", 3, "Shared global variables", "Direct function calls only", "Files on disk", "Events"),
            q("Dependency Injection means…", 1, "Creating all dependencies inside the class", "Supplying a class's dependencies from outside", "Deleting unused classes", "Using only static methods"),
            q("The typical layers of a layered architecture are…", 2, "Input, output, memory", "Client, router, switch", "Presentation, business logic, data", "Model, singleton, factory"),
            q("The Adapter pattern…", 0, "Converts one interface into another that clients expect", "Creates one global instance", "Defines a family of algorithms", "Observes state changes")
        ),

        "cloud" to listOf(
            q("Serverless computing means…", 1, "There are no servers at all", "You don't manage the servers", "Only free hosting", "Offline-only apps"),
            q("Which is an example of IaaS?", 0, "Virtual machines", "Gmail", "Google Docs", "Slack"),
            q("Which is an example of SaaS?", 2, "A raw virtual server", "A container registry", "Gmail", "A load balancer"),
            q("IAM in cloud platforms manages…", 3, "Image assets", "Internet bandwidth", "Instance memory", "Identities and access permissions"),
            q("Autoscaling…", 1, "Shrinks your code", "Adjusts the number of resources to match demand", "Encrypts storage", "Backs up databases"),
            q("Compared with virtual machines, containers…", 0, "Share the host OS kernel and are lighter", "Each need a full guest OS", "Cannot be portable", "Only run on one cloud"),
            q("Kubernetes is used to…", 2, "Write SQL queries", "Design UIs", "Orchestrate and manage containers", "Monitor Wi-Fi"),
            q("Which is an object storage service?", 1, "Amazon EC2", "Amazon S3", "Amazon VPC", "Amazon IAM"),
            q("In the shared responsibility model, the customer is responsible for…", 3, "Physical data centers", "The hypervisor", "Cooling and power", "Their data, configuration and access controls"),
            q("An availability zone is…", 0, "An isolated data center (or group) within a region", "A country's law", "A type of VM", "A billing plan"),
            q("CI/CD pipelines automate…", 2, "Hardware purchases", "Meetings", "Building, testing and deploying code", "Writing documentation"),
            q("Infrastructure as Code means…", 1, "Writing apps without servers", "Managing infrastructure through configuration files", "Buying hardware with code", "Coding on a cloud IDE")
        ),

        "networks" to listOf(
            q("Which OSI layer handles IP addressing and routing?", 2, "Transport", "Data link", "Network", "Session"),
            q("Which protocol is connectionless?", 1, "TCP", "UDP", "HTTP", "FTP"),
            q("DNS translates…", 0, "Domain names to IP addresses", "IP addresses to MAC addresses", "Files to URLs", "Ports to sockets"),
            q("What is the default port for HTTPS?", 3, "21", "80", "25", "443"),
            q("TCP establishes a connection with a…", 1, "Two-way handshake", "Three-way handshake", "Four-way handshake", "Broadcast"),
            q("A typical network switch operates at which OSI layer?", 0, "Data link", "Application", "Transport", "Physical only"),
            q("How many bits are in an IPv4 address?", 2, "16", "64", "32", "128"),
            q("HTTP status 404 means…", 3, "Server error", "Redirect", "Success", "Not found"),
            q("NAT is used to…", 1, "Encrypt web pages", "Translate private IP addresses to public ones", "Resolve domain names", "Assign MAC addresses"),
            q("ARP maps…", 0, "IP addresses to MAC addresses", "Domains to IPs", "Ports to processes", "URLs to files"),
            q("How many total addresses are in a /24 IPv4 subnet?", 2, "128", "254", "256", "512"),
            q("TLS provides…", 1, "Faster routing", "Encryption and authentication", "IP assignment", "Load balancing")
        ),

        "os" to listOf(
            q("Which is a necessary condition for deadlock?", 0, "Circular wait", "Fast CPU", "Large RAM", "Many threads"),
            q("Round Robin scheduling is based on a…", 3, "Priority number", "Stack", "Deadline", "Time quantum"),
            q("A page fault occurs when…", 1, "The disk is full", "A referenced page is not in main memory", "The CPU overheats", "A file is deleted"),
            q("Threads of the same process share…", 2, "Their stacks", "Their registers", "The process's address space", "Nothing"),
            q("A mutex is used to…", 0, "Ensure mutual exclusion on a shared resource", "Speed up the CPU", "Allocate memory", "Encrypt files"),
            q("Kernel mode is…", 1, "A restricted mode for user apps", "A privileged mode with full hardware access", "A power-saving mode", "A debugging tool"),
            q("Which scheduling algorithm can starve long processes?", 2, "Round Robin", "FCFS", "Shortest Job First", "None of them"),
            q("Thrashing is…", 3, "A CPU overheating", "A disk format", "A deadlock type", "Excessive paging that stalls useful work"),
            q("A context switch…", 0, "Saves one process's state and loads another's", "Deletes a process", "Formats memory", "Creates a file"),
            q("LRU page replacement evicts the page that…", 1, "Was loaded first", "Has not been used for the longest time", "Is largest", "Is used most often"),
            q("A semaphore is…", 2, "A type of file", "A scheduling queue", "A synchronization counter", "A network protocol"),
            q("Internal fragmentation is…", 0, "Wasted space inside an allocated block", "Free space between blocks", "A disk error", "A network delay")
        ),

        "dbms" to listOf(
            q("What does the I in ACID stand for?", 2, "Index", "Integrity", "Isolation", "Input"),
            q("Which join returns only rows with matches in both tables?", 0, "INNER JOIN", "LEFT JOIN", "FULL JOIN", "CROSS JOIN"),
            q("A primary key…", 1, "Can contain duplicates", "Uniquely identifies each row", "Must be text", "Is optional"),
            q("Normalization mainly reduces…", 3, "Query speed", "Table names", "Security", "Data redundancy"),
            q("Which SQL command removes rows from a table?", 0, "DELETE", "DROP", "REMOVE", "CUT"),
            q("A database index mainly…", 1, "Encrypts data", "Speeds up reads/lookups", "Backs up tables", "Removes duplicates"),
            q("A foreign key…", 2, "Encrypts a column", "Must be unique", "References a key in another table", "Replaces the primary key"),
            q("Which clause filters groups after GROUP BY?", 3, "WHERE", "ORDER BY", "LIMIT", "HAVING"),
            q("Which is a document-oriented NoSQL database?", 0, "MongoDB", "MySQL", "PostgreSQL", "SQLite"),
            q("A transaction ROLLBACK…", 1, "Saves changes permanently", "Undoes the changes made in the transaction", "Deletes the table", "Locks the database"),
            q("First Normal Form (1NF) requires…", 2, "No foreign keys", "Exactly one table", "Atomic (indivisible) column values", "Only numeric data"),
            q("SELECT DISTINCT…", 0, "Removes duplicate rows from the result", "Sorts the result", "Deletes rows", "Joins two tables")
        ),

        "competitive" to listOf(
            q("For n = 10^5 and a 1-second limit, which complexity fits?", 2, "O(n^2)", "O(n^3)", "O(n log n)", "O(2^n)"),
            q("Binary search requires the data to be…", 1, "Unique", "Sorted (monotonic)", "Positive", "Stored in a list"),
            q("Overlapping subproblems and optimal substructure suggest…", 0, "Dynamic programming", "Brute force only", "Sorting", "Hashing"),
            q("Why do problems often ask for answers modulo 1e9+7?", 3, "It is a prime that looks nice", "To speed up input", "To avoid sorting", "To keep huge results within integer limits"),
            q("The two-pointers technique is commonly used on…", 2, "Unsorted graphs", "Hash tables", "Sorted arrays (e.g. pair sums)", "Binary files"),
            q("What is x XOR x?", 1, "x", "0", "1", "2x"),
            q("The Sieve of Eratosthenes is used to…", 0, "Find all primes up to n", "Sort numbers", "Find shortest paths", "Compute GCD"),
            q("A greedy algorithm works when…", 3, "The input is sorted", "n is small", "There are cycles", "Locally optimal choices lead to a global optimum"),
            q("Prefix sums let you answer range-sum queries in…", 1, "O(n)", "O(1) after O(n) preprocessing", "O(n^2)", "O(log n) per element"),
            q("Union-Find with path compression runs in roughly…", 2, "O(n) per operation", "O(log n) per operation", "Near-constant amortized time", "O(n^2) total"),
            q("About how many simple operations can run in 1 second in contests?", 0, "About 10^8", "About 10^3", "About 10^12", "About 10^15"),
            q("What does n & (n - 1) do?", 3, "Doubles n", "Negates n", "Sets all bits", "Clears the lowest set bit of n")
        )
    )
}

package com.example.cnc

private fun t(title: String, desc: String) = Topic(title, desc)

object CurriculumData {

    private val basic = listOf(
        t("General", "What programming is, how code runs, compilers vs interpreters, and your first program."),
        t("Sequential Logic / Loops", "for, while and do-while loops, nested loops, and loop patterns."),
        t("OOPs", "Classes, objects, encapsulation, inheritance, polymorphism and abstraction."),
        t("Selection / Conditions", "if, else if, else, switch/when, and boolean logic."),
        t("Functions", "Defining functions, parameters, return values, scope and recursion basics."),
        t("Variables & Constants", "Data types, declaring variables, constants, type conversion."),
        t("Data Structures", "Intro to arrays, lists, maps and sets and when to use each."),
        t("Jump Statements", "break, continue, return and how they change control flow.")
    )

    private val pythonDsa = listOf(
        t("Built-in Data Structures", "list, tuple, set, dict, string: operations and time complexity."),
        t("Standard Library Modules", "collections, heapq, bisect, itertools, functools, deque, Counter."),
        t("User-Defined Structures", "Build stacks, queues and classes using Python classes."),
        t("Linked List", "Singly, doubly and circular lists; insert, delete, reverse."),
        t("Trees", "Binary trees, BST, traversals, heaps, balanced trees."),
        t("Graphs", "Adjacency list/matrix, BFS, DFS, shortest paths."),
        t("Tries (Prefix Trees)", "Insert, search and prefix queries for strings."),
        t("Practice", "Curated problems for every Python structure above.")
    )

    private val javaDsa = listOf(
        t("Arrays & Strings", "Arrays, String, StringBuilder and common pitfalls."),
        t("Collections Framework", "ArrayList, LinkedList, HashMap, HashSet, TreeMap, PriorityQueue, Deque."),
        t("Utility Classes", "Arrays, Collections, Optional, Comparator and streams."),
        t("User-Defined Structures", "Custom stacks, queues and generic classes."),
        t("Linked List", "Implement node-based lists with references."),
        t("Trees", "Binary trees, BST, traversals, heaps."),
        t("Graphs", "Adjacency lists with HashMap, BFS, DFS, Dijkstra."),
        t("Tries (Prefix Trees)", "Trie nodes using arrays or HashMaps."),
        t("Practice", "Java-specific problem sets.")
    )

    private val cppDsa = listOf(
        t("Arrays & Strings", "Static arrays, std::string, vectors of vectors."),
        t("STL Containers", "vector, deque, list, set, map, unordered_map, stack, queue, priority_queue."),
        t("STL Algorithms", "sort, binary_search, lower_bound, next_permutation, accumulate."),
        t("User-Defined Structures", "structs, classes, templates and operator overloading."),
        t("Linked List", "Pointer-based lists and memory management."),
        t("Trees", "Binary trees, BST, segment trees, heaps."),
        t("Graphs", "Adjacency list with vectors, BFS, DFS, Dijkstra."),
        t("Tries (Prefix Trees)", "Trie nodes with pointer arrays."),
        t("Practice", "C++-specific problem sets.")
    )

    private val systemDesign = listOf(
        t("Fundamentals", "Requirements, scalability, availability, reliability, latency vs throughput."),
        t("Back-of-the-Envelope Estimation", "Traffic, storage and bandwidth math."),
        t("Load Balancing", "Algorithms, L4 vs L7, health checks."),
        t("Caching", "Cache strategies, eviction policies, CDN, Redis."),
        t("Databases & Sharding", "SQL vs NoSQL, replication, partitioning, consistent hashing."),
        t("Message Queues", "Kafka, RabbitMQ, async processing, pub/sub."),
        t("API Design", "REST, GraphQL, gRPC, rate limiting, pagination."),
        t("CAP Theorem & Consistency", "Trade-offs, eventual consistency, consensus."),
        t("Classic Designs", "URL shortener, chat app, news feed, ride sharing."),
        t("Interview Approach", "A repeatable framework for system design interviews.")
    )

    private val architecture = listOf(
        t("Software Architecture Basics", "What architecture is and why it matters."),
        t("Layered Architecture", "Presentation, business and data layers."),
        t("MVC / MVP / MVVM", "UI patterns, including MVVM on Android."),
        t("Clean Architecture", "Dependency rule, use cases, entities."),
        t("Monolith vs Microservices", "Trade-offs, when to split."),
        t("Event-Driven Architecture", "Events, brokers, CQRS, event sourcing."),
        t("Design Patterns", "Singleton, Factory, Observer, Strategy, Builder."),
        t("SOLID Principles", "Five principles for maintainable code."),
        t("Domain-Driven Design", "Bounded contexts, aggregates, ubiquitous language."),
        t("Documenting Architecture", "Diagrams, ADRs and C4 model.")
    )

    private val cloud = listOf(
        t("Cloud Basics", "IaaS, PaaS, SaaS and public/private/hybrid cloud."),
        t("Compute", "VMs, containers, serverless, autoscaling."),
        t("Storage", "Object, block and file storage."),
        t("Cloud Databases", "Managed SQL, NoSQL and data warehouses."),
        t("Cloud Networking", "VPC, subnets, gateways, DNS."),
        t("Identity & Security", "IAM, roles, encryption, shared responsibility."),
        t("Containers & Kubernetes", "Docker, images, orchestration."),
        t("CI/CD & DevOps", "Pipelines, infrastructure as code."),
        t("Monitoring & Cost", "Logs, metrics, alerts, budgeting."),
        t("Major Providers", "AWS vs Azure vs Google Cloud overview.")
    )

    private val networking = listOf(
        t("Network Basics", "LAN, WAN, topologies and the internet."),
        t("OSI & TCP/IP Models", "Layers and what each does."),
        t("IP Addressing & Subnetting", "IPv4, IPv6, CIDR, NAT."),
        t("TCP vs UDP", "Handshake, reliability, flow and congestion control."),
        t("DNS", "Resolution, records, caching."),
        t("HTTP & HTTPS", "Methods, status codes, TLS, HTTP/2 and 3."),
        t("Routing & Switching", "Routers, switches, ARP, routing protocols."),
        t("Network Security", "Firewalls, VPNs, common attacks."),
        t("Sockets Programming", "Client/server basics."),
        t("Wireless & Mobile Networks", "Wi-Fi, cellular and Bluetooth.")
    )

    private val os = listOf(
        t("OS Basics", "What an OS does, kernel vs user space."),
        t("Processes", "States, PCB, context switching, IPC."),
        t("Threads & Concurrency", "Threads, race conditions, synchronization."),
        t("CPU Scheduling", "FCFS, SJF, Round Robin, priority."),
        t("Deadlocks", "Conditions, prevention, avoidance, detection."),
        t("Memory Management", "Paging, segmentation, fragmentation."),
        t("Virtual Memory", "Page faults, replacement algorithms."),
        t("File Systems", "Files, directories, allocation, journaling."),
        t("I/O & Device Management", "Drivers, interrupts, DMA."),
        t("Linux & Android Internals", "Shell basics and how Android builds on Linux.")
    )

    private val dbms = listOf(
        t("DBMS Basics", "Data, databases, DBMS vs file systems."),
        t("ER Modeling", "Entities, relationships, keys."),
        t("Relational Model", "Tables, keys, constraints."),
        t("SQL Fundamentals", "SELECT, INSERT, UPDATE, DELETE, WHERE."),
        t("Joins & Subqueries", "Inner, outer, cross joins and nested queries."),
        t("Normalization", "1NF to BCNF and denormalization."),
        t("Transactions & ACID", "Isolation levels, commit, rollback."),
        t("Indexing", "B-trees, hash indexes, query plans."),
        t("NoSQL", "Document, key-value, column and graph stores."),
        t("Room & SQLite on Android", "Local databases in Android apps.")
    )

    private val competitive = listOf(
        t("Getting Started", "How contests work and picking a language."),
        t("Fast I/O & Templates", "Input/output tricks and starter templates."),
        t("Complexity Analysis", "Estimate if a solution fits the time limit."),
        t("Sorting & Searching", "Binary search, two pointers, sliding window."),
        t("Greedy Algorithms", "Exchange arguments and classic problems."),
        t("Dynamic Programming", "Knapsack, LIS, LCS, DP on grids and trees."),
        t("Graph Algorithms", "BFS, DFS, DSU, MST, shortest paths."),
        t("Number Theory", "GCD, sieve, modular arithmetic."),
        t("Bit Manipulation", "Masks, XOR tricks, subsets."),
        t("Contest Strategy & Platforms", "Codeforces, LeetCode, CodeChef, AtCoder.")
    )

    val modules = listOf(
        LearnModule("basic", "🌱", "Basic", "8 modules to start from zero", topics = basic),
        LearnModule(
            "dsa", "🧩", "DSA", "Python, Java or C++",
            tracks = mapOf("Python" to pythonDsa, "Java" to javaDsa, "C++" to cppDsa)
        ),
        LearnModule("system_design", "🏗️", "System Design", "Scale real systems", topics = systemDesign),
        LearnModule("architecture", "📐", "Architecture", "Patterns and principles", topics = architecture),
        LearnModule("cloud", "☁️", "Cloud Computing", "Deploy and scale", topics = cloud),
        LearnModule("networks", "🌐", "Computer Network", "How data travels", topics = networking),
        LearnModule("os", "💻", "Operating System", "What runs under your code", topics = os),
        LearnModule("dbms", "🗄️", "DBMS", "Store and query data", topics = dbms),
        LearnModule("custom", "✨", "Create Your Own", "Build your own learning path"),
        LearnModule("competitive", "🏆", "Competitive", "Contest-ready skills", topics = competitive)
    )

    fun byId(id: String?) = modules.firstOrNull { it.id == id }
}

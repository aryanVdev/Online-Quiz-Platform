package com.quizapp;

import java.util.*;

public class InMemoryQuestions {

    public static List<QuizApp.Question> getByExam(String examType) {
        switch (examType) {
            case "GATE": return gate();
            case "JEE Mains": return jeeMains();
            case "JEE Advanced": return jeeAdvanced();
            case "NEET": return neet();
            default: return Collections.emptyList();
        }
    }

    private static List<QuizApp.Question> gate() {
        List<QuizApp.Question> list = new ArrayList<>();
        list.add(q(1, "What is the average time complexity of merge sort", 2019, "O n log n", "O n2", "O log n", "O n", "O n log n", "Divide and conquer", "Merge sort divides and merges in O n per level across log n levels."));
        list.add(q(2, "Which data structure is best for implementing a priority queue", 2018, "Binary heap", "Queue", "Stack", "Linked list", "Binary heap", "Use a heap", "Binary heaps support insert and extract in O log n."));
        list.add(q(3, "Which normal form eliminates partial dependencies", 2017, "2NF", "1NF", "3NF", "BCNF", "2NF", "Composite keys", "2NF removes partial dependency on a subset of a candidate key."));
        list.add(q(4, "Which protocol resolves domain names to IP addresses", 2020, "DNS", "ARP", "DHCP", "ICMP", "DNS", "Name to number", "DNS maps hostnames to IP addresses."));
        list.add(q(5, "Mapping from virtual to physical address in paging is done by", 2016, "Page table", "TLB only", "Cache", "Disk scheduler", "Page table", "Address translation", "OS uses page tables to map virtual pages to frames."));
        list.add(q(6, "Which of the following is not a stable sorting algorithm", 2018, "Selection sort", "Merge sort", "Insertion sort", "Bubble sort", "Selection sort", "Equal keys order", "Selection sort may change the relative order of equal keys."));
        list.add(q(7, "Which OSI layer provides end to end reliable delivery", 2019, "Transport", "Network", "Data link", "Session", "Transport", "Think TCP", "Transport layer handles reliability and flow control."));
        list.add(q(8, "Deadlock cannot occur if which condition is broken", 2021, "Circular wait", "Hold and wait", "Mutual exclusion", "No preemption", "Circular wait", "Coffman conditions", "Breaking any one prevents deadlock; often circular wait."));
        list.add(q(9, "Which traversal of a BST yields keys in sorted order", 2017, "Inorder", "Preorder", "Postorder", "Level order", "Inorder", "Left root right", "Inorder of BST gives ascending order."));
        list.add(q(10, "The best design for O 1 LRU cache operations is", 2022, "Hash map and doubly linked list", "Array", "Queue only", "Stack", "Hash map and doubly linked list", "Move to front", "Map gives O 1 lookup, list maintains recency."));
        list.add(q(11, "Which algorithm is used to detect cycles in a linked list", 2015, "Floyd tortoise and hare", "Kruskal", "Prim", "KMP", "Floyd tortoise and hare", "Two pointers", "Fast and slow pointers meet if there is a cycle."));
        list.add(q(12, "Which algorithm finds shortest paths with non negative weights", 2018, "Dijkstra", "Bellman Ford", "Floyd Warshall", "Prim", "Dijkstra", "Greedy with heap", "Dijkstra uses a priority queue and fails with negative edges."));
        list.add(q(13, "Which cache mapping allows a block to go anywhere in cache", 2015, "Fully associative", "Direct mapped", "Two way set associative", "Four way set associative", "Fully associative", "Most flexible", "Fully associative has highest placement flexibility."));
        list.add(q(14, "What does ACID property I stand for in transactions", 2016, "Isolation", "Integrity", "Indexing", "Idempotence", "Isolation", "Concurrent execution", "Each transaction executes as if alone."));
        list.add(q(15, "Which file allocation method suffers from external fragmentation", 2017, "Contiguous allocation", "Linked allocation", "Indexed allocation", "Hashed allocation", "Contiguous allocation", "Blocks together", "It needs a run of free blocks causing external fragmentation."));
        return list;
    }

    private static List<QuizApp.Question> jeeMains() {
        List<QuizApp.Question> list = new ArrayList<>();
        list.add(q(16, "What is Avogadro number approximately", 2018, "6.02e23", "9.81", "3e8", "1.6e-19", "6.02e23", "Particles per mole", "Avogadro number is the count of particles in one mole."));
        list.add(q(17, "SI unit of power is", 2017, "Watt", "Joule", "Newton", "Volt", "Watt", "Rate of work", "Power is energy per unit time measured in watt."));
        list.add(q(18, "Derivative of sin x equals", 2019, "cos x", "-cos x", "sin x", "-sin x", "cos x", "Standard derivative", "Derivative of sin x equals cos x."));
        list.add(q(19, "pH seven at twenty five C corresponds to", 2016, "Neutral solution", "Acidic solution", "Basic solution", "Strong acid", "Neutral solution", "Water autoionization", "At twenty five C neutral water has pH seven."));
        list.add(q(20, "Gas law relating pressure and temperature at constant volume is", 2018, "Gay Lussac law", "Boyle law", "Charles law", "Avogadro law", "Gay Lussac law", "Pressure temperature", "At constant volume pressure is proportional to temperature."));
        list.add(q(21, "Work done by a constant force equals", 2017, "F dot s", "m a", "p v", "q v", "F dot s", "Displacement", "Work equals component of force along displacement times distance."));
        list.add(q(22, "Unit of magnetic flux is", 2019, "Weber", "Tesla", "Henry", "Ohm", "Weber", "Faraday law", "Magnetic flux is measured in weber."));
        list.add(q(23, "Oxidation is defined as", 2020, "Loss of electrons", "Gain of electrons", "Gain of protons", "Loss of neutrons", "Loss of electrons", "OIL RIG", "Oxidation is loss of electrons and reduction is gain."));
        list.add(q(24, "Which element has highest electronegativity", 2016, "Fluorine", "Oxygen", "Chlorine", "Nitrogen", "Fluorine", "Top right", "Fluorine is the most electronegative element."));
        list.add(q(25, "Speed of light in vacuum is about", 2018, "3e8 m per s", "2e8 m per s", "1e8 m per s", "9.8 m per s2", "3e8 m per s", "Constant c", "Light speed in vacuum is about three times ten to eight meters per second."));
        list.add(q(26, "Boiling point of water at one atmosphere is about", 2017, "100 C", "90 C", "80 C", "70 C", "100 C", "Standard condition", "At one atmosphere pure water boils near 100 C."));
        list.add(q(27, "Which gas turns lime water milky", 2016, "Carbon dioxide", "Oxygen", "Nitrogen", "Hydrogen", "Carbon dioxide", "Calcium carbonate", "CO2 forms insoluble calcium carbonate with lime water."));
        list.add(q(28, "Which particle carries negative charge", 2019, "Electron", "Proton", "Neutron", "Alpha particle", "Electron", "Atomic structure", "Electron carries negative charge."));
        list.add(q(29, "Which rule relates pressure volume and temperature of a gas", 2015, "Ideal gas equation", "Archimedes principle", "Pascal law", "Bernoulli equation", "Ideal gas equation", "PV nRT", "For ideal gas PV equals n R T."));
        list.add(q(30, "Which phenomenon explains blue color of the sky", 2018, "Rayleigh scattering", "Reflection", "Refraction", "Diffraction", "Rayleigh scattering", "Short wavelength scatter", "Shorter wavelengths scatter more causing blue sky."));
        return list;
    }

    private static List<QuizApp.Question> jeeAdvanced() {
        List<QuizApp.Question> list = new ArrayList<>();
        list.add(q(31, "Integral of one by x dx equals", 2019, "ln x plus C", "x", "one by x", "x square", "ln x plus C", "Natural log", "Integral of one by x is natural log of x plus constant."));
        list.add(q(32, "Which gas shows the largest deviation from ideal behavior at high pressure", 2017, "Ammonia", "Helium", "Neon", "Argon", "Ammonia", "Strong interactions", "Ammonia has strong intermolecular forces causing deviation."));
        list.add(q(33, "Moment of inertia of a thin rod about center is proportional to", 2016, "L square", "L", "one by L", "L cube", "L square", "Mass distribution", "For a uniform rod about center I equals one by twelve m L square."));
        list.add(q(34, "Which statement about photoelectric effect is correct", 2018, "There is a threshold frequency", "Current always increases with intensity", "Kinetic energy independent of frequency", "No stopping potential", "There is a threshold frequency", "Einstein equation", "Emission occurs only if photon frequency exceeds threshold."));
        list.add(q(35, "Which of the following is a chiral molecule", 2020, "Carbon with four different groups", "Benzene", "Ethene", "Carbon dioxide", "Carbon with four different groups", "No symmetry plane", "Chirality arises when a carbon has four different groups."));
        list.add(q(36, "Mean value theorem applies when a function is", 2017, "Continuous and differentiable", "Only differentiable", "Only continuous", "Discontinuous", "Continuous and differentiable", "Rolle and MVT", "If conditions hold there exists c with derivative equal to average rate."));
        list.add(q(37, "Which is an example of nucleophile", 2019, "Hydroxide ion", "Sodium ion", "Hydrogen molecule", "Helium atom", "Hydroxide ion", "Electron donor", "Nucleophiles donate an electron pair."));
        list.add(q(38, "The order of a reaction is defined as", 2018, "Sum of powers in rate law", "Number of reactant molecules", "Number of steps", "Number of products", "Sum of powers in rate law", "Rate expression", "Order is determined from the rate law."));
        list.add(q(39, "Electromagnetic wave with highest frequency is", 2016, "Gamma ray", "Radio wave", "Infrared", "Microwave", "Gamma ray", "Short wavelength", "Higher frequency means shorter wavelength."));
        list.add(q(40, "For an ideal gas undergoing isothermal expansion what remains constant", 2018, "Internal energy", "Temperature increases", "Pressure constant", "Entropy decreases", "Internal energy", "U depends on T", "For ideal gas internal energy depends only on temperature."));
        list.add(q(41, "Which algorithm finds minimum spanning tree", 2017, "Kruskal", "Dijkstra", "Bellman Ford", "Ford Fulkerson", "Kruskal", "Greedy cuts", "Kruskal adds edges in increasing weight without cycles."));
        list.add(q(42, "Which rule gives the direction of induced current", 2016, "Lenz law", "Ohm law", "Coulomb law", "Faraday constant", "Lenz law", "Opposes change", "Induced current opposes the change causing it."));
        list.add(q(43, "Which method is used to solve linear differential equations with constant coefficients", 2018, "Characteristic equation", "Laplace only", "Graph method", "Newton method", "Characteristic equation", "Auxiliary roots", "Solve using roots of the characteristic equation."));
        list.add(q(44, "Which quantity is conserved in an elastic collision", 2015, "Both momentum and kinetic energy", "Only momentum", "Only kinetic energy", "Neither", "Both momentum and kinetic energy", "Elastic definition", "In elastic collisions both are conserved."));
        list.add(q(45, "What is the value of integral of sin x from zero to pi", 2016, "2", "0", "1", "-1", "2", "Area interpretation", "Integral of sin x over zero to pi equals 2."));
        return list;
    }

    private static List<QuizApp.Question> neet() {
        List<QuizApp.Question> list = new ArrayList<>();
        list.add(q(46, "Hemoglobin primarily carries which gas in blood", 2019, "Oxygen", "Carbon dioxide", "Nitrogen", "Hydrogen", "Oxygen", "Iron binding", "Hemoglobin binds oxygen via heme iron."));
        list.add(q(47, "Functional unit of kidney is called", 2018, "Nephron", "Neuron", "Alveolus", "Villous", "Nephron", "Filtration", "Nephron performs filtration reabsorption and secretion."));
        list.add(q(48, "Which tissue connects muscle to bone", 2017, "Tendon", "Ligament", "Cartilage", "Areolar", "Tendon", "Connective tissue", "Tendon connects muscle to bone; ligament connects bone to bone."));
        list.add(q(49, "The powerhouse of the cell is", 2016, "Mitochondrion", "Nucleus", "Ribosome", "Golgi body", "Mitochondrion", "ATP synthesis", "Most ATP is produced in mitochondria."));
        list.add(q(50, "The genetic material in most viruses is", 2018, "Either DNA or RNA", "Both DNA and RNA", "Protein", "Lipid", "Either DNA or RNA", "Diversity", "Viruses can have DNA or RNA genomes."));
        list.add(q(51, "Which plant hormone promotes cell elongation", 2017, "Auxin", "Cytokinin", "Ethylene", "Abscisic acid", "Auxin", "Growth regulator", "Auxin promotes cell elongation especially in shoots."));
        list.add(q(52, "Which process produces gametes", 2019, "Meiosis", "Mitosis", "Binary fission", "Budding", "Meiosis", "Reduction division", "Meiosis halves chromosome number to form gametes."));
        list.add(q(53, "Which vitamin deficiency causes scurvy", 2016, "Vitamin C", "Vitamin A", "Vitamin D", "Vitamin K", "Vitamin C", "Collagen", "Vitamin C is needed for collagen synthesis."));
        list.add(q(54, "Which part of brain coordinates balance and posture", 2018, "Cerebellum", "Cerebrum", "Medulla", "Hypothalamus", "Cerebellum", "Motor control", "Cerebellum coordinates movement and balance."));
        list.add(q(55, "What is the primary function of alveoli in lungs", 2017, "Gas exchange", "Blood filtration", "Food absorption", "Sound production", "Gas exchange", "Large surface area", "Alveoli allow diffusion of oxygen and carbon dioxide."));
        list.add(q(56, "Which biome is characterized by low rainfall and sparse vegetation", 2016, "Desert", "Rainforest", "Tundra", "Savanna", "Desert", "Arid climate", "Deserts have very low precipitation and sparse plants."));
        list.add(q(57, "Which protein carries oxygen in muscle", 2018, "Myoglobin", "Hemoglobin", "Actin", "Tubulin", "Myoglobin", "Oxygen store", "Myoglobin stores and transports oxygen in muscles."));
        list.add(q(58, "Which enzyme breaks down starch into maltose", 2015, "Amylase", "Pepsin", "Trypsin", "Lipase", "Amylase", "Saliva and pancreas", "Salivary and pancreatic amylase digest starch."));
        list.add(q(59, "Which organ secretes insulin", 2019, "Pancreas", "Liver", "Spleen", "Kidney", "Pancreas", "Beta cells", "Beta cells in islets of Langerhans secrete insulin."));
        list.add(q(60, "Which blood group is universal donor", 2016, "O negative", "O positive", "AB positive", "AB negative", "O negative", "No A B antigens", "O negative has neither A nor B antigens and no Rh antigen."));
        return list;
    }

    private static QuizApp.Question q(int id, String text, int year,
                                      String a, String b, String c, String d, String correct,
                                      String hint, String explanation) {
        return new QuizApp.Question(id, text, new String[]{a,b,c,d}, correct, hint, explanation, year);
    }
}

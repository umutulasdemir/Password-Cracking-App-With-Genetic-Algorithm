
public class Main {
    
    public static int CHROMOSOME_COUNT = 20;
    static String CODE_TO_CRACK1 = "ChatGPT and GPT-4";
    static String CODE_TO_CRACK2 = "ChatGPT";
    static int popAv = 0; // population average

    public static void main(String[] args) {

        String crackedCode = "";
        for (int i = 0; i < 3; i++) {
            crackedCode = crackTheCode(CODE_TO_CRACK1,i);
        }
        System.out.println("Cracked code: " + crackedCode);
        System.out.println("Population average : " + popAv / 3);
        System.out.println("******************************************************************************");
        popAv = 0; // reset total gen num 
        for (int i = 0; i < 3; i++) {
            crackedCode = crackTheCode(CODE_TO_CRACK2,i);    
        }
        System.out.println("Cracked code: " + crackedCode);
        System.out.println("Population average : " + popAv / 3);
    }

    static String crackTheCode(String code, int iterationNum) {
        long startTime = System.currentTimeMillis();
        String crackedCode = "";
        
        Population population = new Population(CHROMOSOME_COUNT, 1, 2);
        // first generation created randomly
        population.generateFirstGeneration(code.length(), code);
        while (true) {
            population.selectParents();
            population.doElitism();
            population.mutation(code);
            Chromosome bestChild = population.getBestChild(population.chromosomes[0]);
            crackedCode = String.valueOf(bestChild.genesArray);
            if (bestChild.fitnessScore == 0) { // while condition check
                long completionTime = System.currentTimeMillis();
                System.out.println((iterationNum + 1) + ". iteration generation num is: " + population.generationIndex + " algorithm performed in " + ((completionTime - startTime) / 1000f) + " milliseconds");   
                popAv += population.generationIndex;
                break;
            }
        }
        return crackedCode;          
    }
}

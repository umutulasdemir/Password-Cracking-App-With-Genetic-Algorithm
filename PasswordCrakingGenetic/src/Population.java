
import java.util.Arrays;
import java.util.Random;

public class Population {
    static char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!', '"', '#', '$', '%', '&',  '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', ']', '^', '_', '`', '{', '|', '}', '~', ' '};
    int COUNT_CHR = 20;
    Random rand = new Random();
    int populationLength, generationIndex, eliteUnitsNum;
    Chromosome[] chromosomes, parents;
    
    // constructor with parameters
    public Population(int populationLength, int generationNum, int eliteUnitsNum) {
        this.populationLength = populationLength;
        this.generationIndex = generationNum;
        this.eliteUnitsNum = eliteUnitsNum;
        chromosomes = new Chromosome[populationLength];
        parents = new Chromosome[COUNT_CHR];
    }

    // constructor without parameters
    public Population() {
        this.populationLength = 0;
        this.generationIndex = 0;
        this.chromosomes = null;
        this.parents = null;
        this.eliteUnitsNum = 0;
    }

    public void generateFirstGeneration(int len, String code) {        
        for (int i = 0; i < populationLength; i++) {
            Chromosome newChromosome = new Chromosome(len);
            for (int j = 0; j < newChromosome.chromosomeLength; j++) {
                newChromosome.genesArray[j] = CHARACTERS[rand.nextInt(CHARACTERS.length)];
            }
            newChromosome.calculateFitness(code);
            chromosomes[i] = newChromosome;
        }
    }

    // selection
    public void selectParents() {
        Chromosome[] chromosomesTemp = chromosomes;
        Arrays.sort(chromosomes);
        System.arraycopy(chromosomesTemp, 0, parents, 0, COUNT_CHR);
    }
    
    // get best child
    public Chromosome getBestChild(Chromosome bestChild){
        for (Chromosome chromosome : chromosomes) {
            if (chromosome.fitnessScore < bestChild.fitnessScore) {
                bestChild = chromosome;
            }
        }
        return bestChild;
    }

    // crossbreeding
    public Chromosome doCrossBreeding(Chromosome parent1, Chromosome parent2) {

        // create child
        Chromosome child = new Chromosome(chromosomes[0].chromosomeLength);

        int firstGen = (int) (Math.random() * chromosomes[0].chromosomeLength);
        int secondGen = (int) (Math.random() * chromosomes[0].chromosomeLength);

        // crosbreed randomly chosen genes
        for (int chr_index = 0; chr_index < chromosomes[0].chromosomeLength; chr_index++) {
            if (chr_index < Math.min(firstGen, secondGen) || chr_index > Math.max(firstGen, secondGen)) {
                child.genesArray[chr_index] = parent1.genesArray[chr_index];
            } else {
                child.genesArray[chr_index] = parent2.genesArray[chr_index];
            }
        }
        return child;
    }

    // elitism
    public void doElitism() {
        Chromosome[] children = new Chromosome[populationLength];

        for (int i = 0; i < eliteUnitsNum + 1; i++) {
            children[i] = parents[i];
        }

        for (int i = eliteUnitsNum; i < populationLength; i++) {
            //selection and breeding
            Chromosome parent1 = parents[(int)(Math.random() * COUNT_CHR)];
            Chromosome parent2 = parents[(int)(Math.random() * COUNT_CHR)];

            children[i] = doCrossBreeding(parent1,parent2);

        }
        chromosomes = children;
    }

    //mutation
    public void mutation(String code) {
        for (int i = 0; i < populationLength; i++) {
            if (Math.random() < 0.1) { // do mutation when random number lower than 0.1
                // choose gene randomly to mutate
                int randomGeneToMutate = (int) (Math.random() * chromosomes[0].chromosomeLength);
                chromosomes[i].genesArray[randomGeneToMutate] = CHARACTERS[rand.nextInt(CHARACTERS.length)];
            }
            chromosomes[i].calculateFitness(code);
        }
        generationIndex++;
    }
}

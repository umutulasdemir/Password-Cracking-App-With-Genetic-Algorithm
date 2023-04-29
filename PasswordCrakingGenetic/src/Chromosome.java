
public class Chromosome implements Comparable<Chromosome> {
    char[] genesArray;
    int chromosomeLength;
    int fitnessScore;

    // constructor with parameters
    public Chromosome(int chromosomeLength) {
        this.chromosomeLength = chromosomeLength;
        genesArray = new char[chromosomeLength];
    }

    // constructor without parameters
    public Chromosome() {
        this.genesArray = null;
        this.chromosomeLength = 0;
    }

    public void calculateFitness(String code) {
        for (int i = 0; i < code.length(); i++) if (genesArray[i] != code.charAt(i)) fitnessScore++;  
    }

    @Override
    public int compareTo(Chromosome o) {
        return this.fitnessScore - o.fitnessScore;
    }
}


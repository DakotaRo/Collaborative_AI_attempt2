import java.util.Random;

public class SimpleDemoGA 
{
    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;
    public static void main(String args[])
    {
        Random rn = new Random();
        SimpleDemoGA demo = new SimpleDemoGA();

        //initialize population
        demo.population.initializePopulation(10);

        //calculate fitness of each individual
        demo.population.calculateFitness();
        
        System.out.println("Generation: " + demo.generationCount + " Fettest: " + demo.population.fittest);

        //while population gets an individual with maximum fitness
        while (demo.population.fittest < 5)
        {
            ++demo.generationCount;

            //do selection
            demo.selection();

            //do crossover
            demo.crossover();

            //do mutation under a random probability
            if (rn.nextInt()%7 < 5)
            {
                demo.mutation();
            }

            //add fittest offspring to population
            demo.addFittestOffspring();

            //calculate new fitness value
            demo.population.calculateFitness();

            System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
        }

        System.out.println("\nSolution nfound in generation " + demo.generationCount);
        System.out.println("Fitness: " + demo.population.getFittest().fitness);
        System.out.print("Genes: ");
        for (int i = 0; i < 5; i++)
        {
            System.out.print(demo.population.getFittest().genes[i]);
        }
        System.out.println("");
    }

    //selection
    void selection()
    {
        //select the fittest individual
        fittest = population.getFittest();

        //select the second fittest individual
        secondFittest = population.getSecondFittest();
    }
    //crossover
    void crossover()
    {
        Random rn = new Random();

        //select a random crossover point
        int crossOverPoint = rn.nextInt(population.individuals[0].geneLength);

        //swap values among parents
        for (int i = 0; i < crossOverPoint; i++)
        {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;
        }
    }

    //mutation
    void mutation()
    {
        Random rn = new Random();

        //select a random mutation point
        int mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        //flip values at the mutation point
        if (fittest.genes[mutationPoint] == 0)
        {
            fittest.genes[mutationPoint]==1;
        }
        else
        {
            fittest.genes[mutationPoint]=0;
        }

        mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        if (secondFittest.genes[mutationPoint] == 0)
        {
            secondFittest.genes[mutationPoint] = 1;
        }
        else
        {
            secondFittest.genes[mutationPoint] = 0;
        }
    }

    //get fittest offspring
    Individual geFittestOffspring()
    {
        if(fittest.fitness > secondFittest.fitness)
        {
            return fittest;
        }
        return secondFittest;
    }

    //replace least fit individual from fittest offspring
    void addFittestOffspring()
    {
        //update fitness values of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();

        //get index of least fit individual
        int leastFittestIndex - population.getLeastFittestIndex();

        //replace least fittest individual from most fit offspring
        population.individuals[leastFittestIndex] = getFittestOffspring();
    }
}

//individual class
class Individual{
    int fitness = 0;
    int[] genes = new int[5];
    int geneLength = 5;

    public Individual()
    {
        Random rn = new Random();

        //set genes randomly for each individual
        for (int i = 0; i < genes.length; i++)
        {
            genes[i] = Math.abs(rn.nextInt() % 2);
        }

        fitness = 0;
    }

    //calculate fitness
    public void calcFitness()
    {
        fitness = 0;
        for(int i = 0; i<5; i++)
        {
            if(genes[i] == 1)
            {
                ++fitness;
            }
        }
    }
}

//population class
class Population
{
    int popSize = 10;
    Individual[] individuals = new Individual[10];
    int fittest = 0;

    //initialize population
    public void initializePopulation(int size)
    {
        for(int i = 0; i < individuals.length; i++)
        {
            individuals[i] = new Individual();
        }
    }

    //get the fittest individual
    public Individual getFittest()
    {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.length; i++)
        {
            if (maxFit <= individuals[i].fitness)
            {
                maxFit = individuals[i].fitness;
                maxFitIndex = i;
            }
        }
        fittest = individuals[maxFitIndex].fitness;
    }
}
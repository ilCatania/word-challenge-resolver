package it.gcatania.wordchallenge.resolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * @author gabriele.catania
 */
public class Resolver
{

    public static final int MIN_LETTERS = 3;

    public static final int MAX_LETTERS = 6;

    public static final int RESULTS_PER_LINE = 5;

    private List<String> dict;

    public static void main(String[] args)
    {
        try
        {
            Resolver res = new Resolver("/it.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true)
            {
                System.out.println("Enter "
                    + MAX_LETTERS
                    + " letters and press enter (type \"q\" and press enter to quit): ");
                String letters = br.readLine();
                if (letters.equalsIgnoreCase("q"))
                {
                    break;
                }
                System.out.println();
                res.outputWords(letters);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Resolver(String dictionaryPath) throws IOException
    {
        InputStream is = getClass().getResourceAsStream(dictionaryPath);
        dict = new ArrayList<String>(60000);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String word = br.readLine();
        while (word != null)
        {
            if (word.length() >= MIN_LETTERS && word.length() <= MAX_LETTERS)
            {
                dict.add(word);
            }
            word = br.readLine();
        }
        br.close();
    }

    public void outputWords(String letters)
    {
        char[] chars = letters.toCharArray();
        int newline = RESULTS_PER_LINE;
        for (String word : dict)
        {
            if (Resolver.isLegalWord(word, chars))
            {
                System.out.print(word);
                char sep;
                if (--newline == 0)
                {
                    newline = RESULTS_PER_LINE;
                    sep = '\n';
                }
                else
                {
                    sep = '\t';
                }
                System.out.print(sep);
            }
        }
        System.out.println();
    }

    private static boolean isLegalWord(String word, char[] charsToUse)
    {
        char[] charsToMatch = word.toCharArray();
        for (char toUse : charsToUse)
        {
            for (int i = 0; i < charsToMatch.length; i++)
            {
                char toMatch = charsToMatch[i];
                if (toUse == toMatch)
                {
                    if (charsToMatch.length == 1)
                    {
                        return true;
                    }
                    charsToMatch = remove(charsToMatch, i);
                    break;
                }

            }
        }
        return false;
    }

    private static char[] remove(char[] array, int indexToRemove)
    {
        int lastIndex = array.length - 1;
        char[] result = new char[lastIndex];
        if (indexToRemove > 0)
        {
            System.arraycopy(array, 0, result, 0, indexToRemove);
        }
        if (indexToRemove < lastIndex)
        {
            System.arraycopy(array, indexToRemove + 1, result, indexToRemove, lastIndex - indexToRemove);
        }

        return result;
    }
}

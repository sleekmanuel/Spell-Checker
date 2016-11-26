/*
* Data Structures
* Programming Project 2
* 
*  implementation of SeparateChainingHashTable
*/
import java.util.*;

public class SeparateChainingHashTable<AnyType> {
	
	private static final int DEFAULT_TABLE_SIZE = 152017;
	
	private List<AnyType>[] theLists;
	private int currentSize;
	
	/* Default constructor */
	public SeparateChainingHashTable()
	{
		this(DEFAULT_TABLE_SIZE);
	}
	
	/* Constructor */
	@SuppressWarnings("unchecked")
	public SeparateChainingHashTable(int size)
	{
		theLists = new LinkedList[nextPrime(size)];
		
		for (int i = 0; i < theLists.length; i++)
			theLists[i] = new LinkedList<>();
	}
	
	/* Insert */
	public void insert(AnyType x)
	{
		List<AnyType> whichList = theLists[myhash(x)];
		if (!whichList.contains(x))
		{
			whichList.add(x);
			
			/* Rehash if needed */
			if (++currentSize > theLists.length)
				rehash();
		}
	}
	
	/* Remove */
	public void remove(AnyType x)
	{
		List<AnyType> whichList = theLists[myhash(x)];
		if (whichList.contains(x))
		{
			whichList.remove(x);
			currentSize--;
		}
	}
	
	/* Check if an item is in the table */
	public boolean contains(AnyType x)
	{
		List<AnyType> whichList = theLists[myhash(x)];
		return whichList.contains(x);
	}
	
	/* Clear the table */
	public void makeEmpty()
	{
		for (int i = 0; i < theLists.length; i++)
			theLists[i].clear();
		currentSize = 0;
	}
	
	/* Print the hash table */
	public void print()
	{
		for (int i = 0; i < theLists.length; i++)
		{
			System.out.print(i + ": ");
			for (AnyType item : theLists[i])
				System.out.print(item + " ");
			System.out.println();
		}
		System.out.println();
	}
	
	/* Rehash table */
	@SuppressWarnings("unchecked")
	private void rehash()
	{
		List<AnyType>[] oldLists = theLists;
		
		/* Create a a new double-sized, empty table */
		theLists = new List[nextPrime(2 * theLists.length)];
		for (int j = 0; j < theLists.length; j++)
			theLists[j] = new LinkedList<>();
			
		/* Copy table over */
		currentSize = 0;
		for (int i = 0; i < oldLists.length; i++)
			for (AnyType item : oldLists[i])
				insert(item);
	}
	
	/* Hash function */
	private int myhash(AnyType x)
	{
		int hashVal = x.hashCode();
		
		hashVal %= theLists.length;
		if (hashVal < 0)
			hashVal += theLists.length;
		
		return hashVal;
		
	}
	
	/* Get the next prime */
	private static int nextPrime(int n)
	{
		if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
	}
	
	/* Check if n is prime */
	private static boolean isPrime(int n)
	{
		if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
	}
	
	
	
}

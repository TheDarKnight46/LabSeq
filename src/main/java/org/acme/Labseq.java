package org.acme;

import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/labseq")
public class Labseq {
    private Map <Long, Long> cache = new HashMap<>();

    /**
     * Adds a value to the cache Map with the respective key and value.
     * @param n is the key for the new Map entry.
     * @param value is the value for the new Map entry.
     */
    private void cacheValue(long n, long value) {
        cache.put(n, value);
    }
    
    /**
     * Calculates the lab sequence value for parameter n.
     * If n <= 3 the function provides the result immediatly as the return value.
     * Else the function checks if the number requested has been previously cached in the class.
     *      TRUE - returns the cached number skipping further calculations.
     *      FALSE - the function becomes RECURSIVE, calling itself and reducing the value of n according to the function: f(n) = f(n-4) + f(n-3)
     * @param n number used to determine the value of the function.
     * @return result of calculated function.
     */
    private long breakdown(long n) {
        if (n <= 3)
            return (n % 2 == 0) ? 0 : 1; //condensed if statement. if true return 0, else return 1
        else {
            Long cachedNum = cache.get(n);
            if (cachedNum != null)
                return cachedNum;   

            long result = breakdown(n-4) + breakdown(n-3); //recursive call
            cacheValue(n, result);
            return result;
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{number}")
    /**
     * Starts the calculation of the Lab Sequence of specified number
     * @param number number passed through the endpoint
     * @return string to print to the browser window
     */
    public String calculateFunction(String number) {
        try {
            long n = Long.valueOf(number);
            return String.format("\nL(%d) = %d\n", n, breakdown(n));
        } 
        catch (NumberFormatException e) {
            return "Invalid Number Inserted";
        }
    }
} 

package org.acme;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

@Path("/labseq")
public class Labseq {
    private Map <Integer, BigInteger> cache = new HashMap<>();

    /**
     * Adds a value to the cache Map with the respective key and value.
     * @param n is the key for the new Map entry.
     * @param value is the value for the new Map entry.
     */
    private void cacheValue(int n, BigInteger value) {
        cache.put(n, value);
    }
    
    /**
     * Calculates the lab sequence value for parameter n.
     * If n <= 3 the function provides the result immediately as the return value.
     * Else the function checks if the number requested has been previously cached in the class.
     *      TRUE - returns the cached number skipping further calculations.
     *      FALSE - the function becomes RECURSIVE, calling itself and reducing the value of n according to the function: f(n) = f(n-4) + f(n-3)
     * @param n number used to determine the value of the function.
     * @return result of calculated function. Has to return BigInteger due to the number size that can be achieved when calculating numbers in the 1000+ range of n.
     */
    private BigInteger breakdown(int n) {
        if (n <= 3)
            return (n % 2 == 0) ? BigInteger.valueOf(0) : BigInteger.valueOf(1); //condensed if statement. if true return 0, else return 1
        else {
            BigInteger cachedNum = cache.get(n);
            if (cachedNum != null)
                return cachedNum;   

            BigInteger result = breakdown(n-4).add(breakdown(n-3)); //recursive call
            cacheValue(n, result);
            return result;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{number}")
    /**
     * Starts the calculation of the Lab Sequence of specified number. Result is calculated with BigInteger.
     * @param number number passed through the endpoint
     * @return string to print to the browser window
     */
    public Response calculateFunction(String number) {
        try {
            int n = Integer.valueOf(number);
            BigInteger result = breakdown(n);

            return Response.ok(String.format("L(%d) = %d\n", n, result)).build();
        } 
        catch (NumberFormatException e) {
            return Response.ok("Invalid Number Inserted").build();
        }
    }
} 

# Labseq Challenge

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

The following text represents the steps taken to reach the final product.

## Installing Quarkus

Following the guide provided in the website: https://quarkus.io/get-started/, the Quarkus CLI was installed for Powershell using:

```shell script
iex "& { $(iwr https://ps.jbang.dev) } trust add https://repo1.maven.org/maven2/io/quarkus/quarkus-cli/"
```

```shell script
iex "& { $(iwr https://ps.jbang.dev) } app install --fresh --force quarkus@quarkusio"
```

After installing the CLI, the next step is to create an Application. This can be done by using the following command:

```shell script
quarkus create AppName
```

After creating the application, enter the new directory and run it using the following command:

```shell script
quarkus dev
```

Your Quarkus app is now running at localhost:8080. For the purposes of this challenge neither the IP nor the Port were changed.

## Creating the Java code

When it came to the programming side of the challenge it was first required that the problem was understood.

By analyzing the provided document, the main takeaways from the challenge were the following:
- Implement a REST Service, using the Quarkus Java Framework;
- This service returned a value from the labseq sequence;
- The way the sequence of values works if defined by:
    - n=0 -> *l(0) = 0* 
    - n=1 -> *l(1) = 1* 
    - n=2 -> *l(2) = 0* 
    - n=3 -> *l(3) = 1* 
    - n>3 -> *l(n) = l(n-4) + l(n-3)*
- The endpoint created should be in the form <baseurl>/labseq/{n} where {n} represents the index of the sequence’s value to return;
- The implemented service should use a caching mechanism;
- The Calculation of l(10000) must be correctly returned in under 10s.

### Understanding the sequence

To understand how the sequence works, firstly some by hand calculations were performed, to understand how to best go about the coding part.
It became clear that the way larger numbers were calculated was by slowly reducing their size until it was either 0,1,2 or 3.
*This is was clearly a recursive process*

It was also necessary to understand how the caching of data was going to be done.
By saving the result of already calculated l(n) the processing load and time required would be cut tremendously.
The best way to store data like this is by using a **Map** where the *keys* are the values of *n* and the *values* are the values of *l(n)*. This variable is called **cache**

### Using recursive functions

When creating the algorithm to calculate the sequence, the method of the class **Labseq** called *breakdown(long n)* is used.
This method is being used recursively.

It first checks if the value of *n* is lesser or equal to 3 (*n<=3*). 
    If so it gives the direct answer as provided by the source document.
    If it is not, then the formula is applied (*l(n-4) + l(n-3)*) calling itself again with the new values of *n*.

### Caching data

When the recursive function ends, it goes back and adds the value to the map using its respective *keys* and *values*.
For this to work though, it requires the method *breakdown* to check the Map every time it is executed, looking within the **cache** for the respective *key*.
If it find the *key*, then the *value* is returned immediately, otherwise the execution continues as previously explained.
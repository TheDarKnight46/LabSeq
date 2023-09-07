# Labseq Challenge

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

The following text represents the steps taken to reach the final product.

## Installing Quarkus

Folllowing the guide provided in the website: https://quarkus.io/get-started/, the Quarkus CLI was installed for Powershell using:

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

By analysing the provided document, the main takeaways from the challenge were the following:
- Implement a REST Service, using the Quarkus Java Framework;
- This service returned a value from the labseq sequence;
- The way the sequence of values works if defined by:
    - n=0 => l(0) = 0 
    - n=1 => l(1) = 1 
    - n=2 => l(2) = 0 
    - n=3 => l(3) = 1 
    - n>3 => l(n) = l(n-4) + l(n-3)
- The endpoint created should be in the form <baseurl>/labseq/{n} where {n} represents the index of the sequence’s value to return;
- The implemented service should use a caching mechanism;
- The Calculation of l(10000) must be correctly returned in under 10s.

### Understanding the problem

### Using recursive functions


### Caching data

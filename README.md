# Labseq Challenge

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

The following text represents the steps taken to reach the final product.

## Backend - Installing Quarkus

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

or alternatively, if `Maven` is installed and configured:

```shell script
mvn compile quarkus:dev
```
> **_NOTE:_** To configure maven, follow the README file bundled with the maven download or this guide for Windows https://phoenixnap.com/kb/install-maven-windows.

Your Quarkus app is now running at localhost:8080. For the purposes of this challenge neither the IP nor the Port were changed.

## Frontend - Installing Angular and Nodejs



## Project Structure

The project was structured using this template provided in https://marcelkliemannel.com/articles/2021/bundling-quarkus-with-web-frameworks-like-angular-react-vue-js-in-maven/

```
project/
├── pom.xml
├── frontend/
│   ├── app/
│   │   ├── src/
│   │   ├── public/
│   │   │   ├── ...
│   │   │   └── index.html
│   │   ├── ...
│   │   └── package.json
│   └── pom.xml
├── backend/
│   ├── src/main/java/org.example/project/
│   │   └── ApiResource.java
│   └── pom.xml
└── distribution/
    └── pom.xml
```

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
- The calculation of `l(10000)` must be correctly returned in under 10s.

### Understanding the sequence

To understand how the sequence works, firstly some by hand calculations were performed, to understand how to best go about the coding part.

It became clear that the way larger numbers were calculated was by slowly reducing their size until it was either 0,1,2 or 3.

Caching was an important step since by saving the result of already calculated `l(n)` the processing load and time required would be cut **tremendously**.

The best way to store data like this is by using a `Map` where the '*keys*' are the values of *n* and the '*values*' are the values of `l(n)`. This variable is called `cache`.

### Using recursive functions

When creating the algorithm to calculate the sequence, the method of the class `Labseq` called `breakdown(int n)` is used.

```java
1  private BigInteger breakdown(int n) {
2     if (n <= 3)
3         return (n % 2 == 0) ? BigInteger.valueOf(0) : BigInteger.valueOf(1); 
4     else {
5         BigInteger cachedNum = cache.get(n);
6         if (cachedNum != null)
7             return cachedNum;   
8
9         BigInteger result = breakdown(n-4).add(breakdown(n-3)); 
10        cacheValue(n, result);
11        return result;
12    }
13 }
```

This method is being used recursively.

It first checks if the value of *n* is lesser or equal to 3 (*n<=3*), as seen in line 3. 

If so it gives the direct answer as provided by the source document.

If it is not, then the formula is applied (*l(n-4) + l(n-3)*) calling itself again with the new values of *n*.

> **_NOTE:_** This formula implementation can be seen in line 9. Since its a BigInteger it is required to use the add() method to perform a sum.

### Caching data

When the recursive function ends, it goes back and adds the value to the map using its respective *keys* and *values*.

For this to work though, it requires the method *breakdown* to check the Map every time it is executed, looking within the **cache** for the respective *key*.

If it find the *key*, then the *value* is returned immediately, otherwise the execution continues as previously explained.

## Solution

The solution to the the function `l(10000)` is presented below.

> **_NOTE:_** Due to the size of the result, the code had to be adapted to use BigInteger (32 bit) so that the number could be calculated properly.

**l(10000) = 69950566878097184013157744477635556727868849589082998911839343197880823215346221009722233023943602770307729191665655398407165768121564186987192397693071609846919453430811144389823875683774480880281479951416523467736343974525549960389427464841013320746241755697990287429747307066048835194835534301361435435171244963037487135503198565459610125773779110841477593382691667903942271834984619627946845583317271714790127086723614783681640902031022970893247841818337935296805019561967546398282596597404334400595273408222818081762762981879844447410743730739725556081175617700994424267694361314464204552899258977619983936670456553201627025301979470684612183482967552781789171894406131379502874476544298881442363169258726593616997962614541232149734611181684936265928412294383549494959124156102645749161099774806409315657803977415799277767229630141831326718534674913706653355139**
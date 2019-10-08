# Big Data Exercises

This repo contains several common big data exercises.

* **MovieRecommender** Uses Amazon movie reviews sample data   [stanford.edu/data/web-Movies.html](http://snap.stanford.edu/data/web-Movies.html) for a simple movie recommender

    
 
 
## Setup

1. Install the  JDK 7.0
2. [Download & Install Maven](http://maven.apache.org/download.cgi)
   
 
## How to run tests

    Before running tests create resources folder on main folder
    Make sure to download and uncompres the file [http://snap.stanford.edu/data/movies.txt.gz]
    On the testDataInfo class find the Recommender instance param, replace it with "movies.txt"
    #from the repository root 
    mvn test

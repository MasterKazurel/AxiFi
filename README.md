# AxiFi (by CyberDyne)

A financial tracking program.

## Releases
**Latest:** 

- [AxiFi-1.0](https://drive.google.com/file/d/0B6sC8KDRHN9SMVFQWDBQWU9sajg/view?usp=sharing)

Previous:
- None


## Developers
### Testing
General testing can be performed by running as a standard Java application in eclipse (no build required). There is also 
unit testing included for many of the classes.

### Deployment
This project uses Maven to generate an executable JAR file.

The easiest way to build is from Eclipse. Just press **Alt + Shift + x**, then **M** to run the Maven build, or right-click 
on the project > Run as > Maven Build.

Once Maven finishes building, the new executable JAR will be in **/target/**.

Alternatively, you can [install Maven](http://www.baeldung.com/install-maven-on-windows-linux-mac) and run 
`mvn package` from the project's root directory.

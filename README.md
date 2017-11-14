# AxiFi (by CyberDyne)

A financial tracking program.

<<<<<<< HEAD
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
=======
## Releases (hosted on Google Drive)
**Latest:**

| OS        | Installer           |
| ------------- |:-------------:|
| Windows      | [AxiFi-1.0.zip](https://drive.google.com/open?id=1SFM7bqRWnBe22p3wkULdCDlvGpXPU--p) |
| MacOS      | Currently unavailable      |
| Linux | Currently unavailable       |

Previous releases:
- None

## Developers

## Testing
Can be run as a normal Java application in eclipse. The main method is in **MainApp.java**.

### Deployment
This project uses the  [e(fx)clipse](https://www.eclipse.org/efxclipse/index.html) Eclipse extension, and subsequently [Apache Ant](https://ant.apache.org/), to generate various installers for Windows (.exe), MacOS (.dmg), and Linux (.deb).

After installing e(fx)clipse, the installer can be built by opening **build.fxbuild** and clicking "_Generate ant build.xml and run_" in the **Building_Exporting** section on the top right.

This will generate the installers in **\build\deploy**.
>>>>>>> cf6ff7618d32b98d99669c8af92131ba2e9f53f3

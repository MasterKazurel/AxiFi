# AxiFi (by CyberDyne)

A financial tracking program.

## Releases (hosted on Google Drive)
**Latest:**

| OS        | Installer           |
| ------------- |:-------------:|
| Windows (.exe)      | [AxiFi.zip](https://drive.google.com/open?id=107Woeakf31HL-bqi9QOiRc_sHSLcQdSW) |
| MacOS      | Currently unavailable      |
| Linux | Currently unavailable       |
| All (JAR) | [AxiFi.jar.zip](https://drive.google.com/open?id=1df_M1xcUbglGZ-eBVt_tikgWLCNG9dbm) |

## Developers

### Testing
Can be run as a normal Java application in eclipse. The main method is in **MainApp.java**.

### Deployment
This project uses the  [e(fx)clipse](https://www.eclipse.org/efxclipse/index.html) Eclipse extension, and subsequently [Apache Ant](https://ant.apache.org/), to generate various installers for Windows (.exe), MacOS (.dmg), and Linux (.deb).

After installing e(fx)clipse, the installer can be built by opening **build.fxbuild** and clicking "_Generate ant build.xml and run_" in the **Building_Exporting** section on the top right.

This will generate the installers in **\build\deploy**.

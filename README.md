# AxiFi (by CyberDyne)

A financial tracking program.

## Releases (hosted on Google Drive)
**Latest:**

| OS        | Installer           |
| ------------- |:-------------:|
| Windows      | [AxiFi-1.0.zip](https://drive.google.com/file/d/0B6sC8KDRHN9Sd3cyVl9vT0lLbk0/view?usp=sharing) |
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

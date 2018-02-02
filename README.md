# AxiFi (by CyberDyne)

A financial tracking program.

## Releases (hosted on Google Drive)
**Latest:**

| OS        | Installer           |
| ------------- |:-------------:|
| Windows (.exe)      | [AxiFi.zip](https://doc-0k-3s-docs.googleusercontent.com/docs/securesc/79jc4rov0ns9bhk23k0q5pl5rn7bn79l/4dv0dcfqasuj5hb5qti88ud0bhdl53ko/1517558400000/06692395190575316198/06692395190575316198/1xqU2uFOyUw_ap7mkERBDY3F2QPTqLxQt?e=download&nonce=8apqib8dk8d6u&user=06692395190575316198&hash=gj2o5sg4144il7uqe0kef2p8lk7n918o) |
| MacOS      | Currently unavailable      |
| Linux | Currently unavailable       |
| All (JAR) | [AxiFi.jar.zip](https://doc-04-3s-docs.googleusercontent.com/docs/securesc/79jc4rov0ns9bhk23k0q5pl5rn7bn79l/368pctk954pl0li49t0knnkobpl2alrk/1517558400000/06692395190575316198/06692395190575316198/1df_M1xcUbglGZ-eBVt_tikgWLCNG9dbm?e=download) |

## Developers

### Testing
Can be run as a normal Java application in eclipse. The main method is in **MainApp.java**.

### Deployment
This project uses the  [e(fx)clipse](https://www.eclipse.org/efxclipse/index.html) Eclipse extension, and subsequently [Apache Ant](https://ant.apache.org/), to generate various installers for Windows (.exe), MacOS (.dmg), and Linux (.deb).

After installing e(fx)clipse, the installer can be built by opening **build.fxbuild** and clicking "_Generate ant build.xml and run_" in the **Building_Exporting** section on the top right.

This will generate the installers in **\build\deploy**.

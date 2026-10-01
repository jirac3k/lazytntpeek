Lazy TNT Peek - Fabric client mod for Minecraft 1.21.11. Ctrl+H toggles.

OPTION A - build on GitHub (no installs):
  1. Create a new GitHub repo, upload everything in this folder (keep the .github folder).
  2. Open the Actions tab -> "build" -> wait ~3 min -> download artifact "lazytntpeek-jar".
  3. Unzip it; put the .jar in .minecraft/mods with Fabric Loader 0.18+ and Fabric API 0.141.1+1.21.11.

OPTION B - build locally:
  Install JDK 21 and Gradle 9.2+, then in this folder run:  gradle build
  Jar: build/libs/lazytntpeek-1.0.0.jar

If compile fails on Screen.hasControlDown(), replace it with
  net.minecraft.client.Minecraft.getInstance().hasControlDown()

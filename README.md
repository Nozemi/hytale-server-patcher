# Hytale Server Patcher
This is a small project to apply certain patches to Hytale's game server. This is intended to solve things that can't be solved with normal plugins.

To use this; simply clone the project and build the JAR, then put it inside the server's `earlyplugins`-folder. You may want to consider running your server with the `--accept-early-plugins`-argument as well.

## Current Features
- Removes the `Player has left world` message entirely.

## Credits
- [Bootstrap/Early Plugins - Hytale Modding Documentation (britakee-studios.gitbook.io)][bootstrap-earlyplugins-docs]
  - Provided just enough information on what an early plugin actually is, and general idea of how to implement one.


[bootstrap-earlyplugins-docs]: https://britakee-studios.gitbook.io/hytale-modding-documentation/plugins-java-development/09-bootstrap-early-plugins
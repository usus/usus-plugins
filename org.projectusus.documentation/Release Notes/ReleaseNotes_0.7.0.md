# Usus 0.7.0 Release Notes

## New Features

### Select all classes in same package

When you are in the Usus Class Graph and a node is selected, you can now select all classes in the same package from the context menu.

### Contracts for Java (C4J)

Some of the classes in Usus Core now have contracts. We will add more of them over time. For the interested, those can be enabled as described in the
[Wiki](http://code.google.com/p/projectusus/wiki/Contracts4Java).

## Bug Fixes

### Import Declarations

Usus erroneously considered import declarations to be relevant for the ACD calculation. It also accidentally attributed them to the wrong type. Now import declarations are no longer considered for the ACD because they are not part of any class.

### Inner Types

In ACD calculation, there was a bug regarding inner types: A reference to a type that appeared in an outer type after an inner type had been declared would erroneously get connected to the inner type. Now the connection is established with the outer type.

### Method/field chaining

Usus was unable to detect dependencies between classes when methods or fields were chained.

### Open Hotspots in e4

Double-clicking hotspots to open them was broken in Eclipse 4.

## Feedback

Please let us know what you think. If anything is not working in a way you'd expect, feel free to open an issue on GitHub: https://github.com/usus/usus-plugins/issues

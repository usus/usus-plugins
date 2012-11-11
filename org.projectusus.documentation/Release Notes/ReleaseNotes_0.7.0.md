# Usus 0.7.0 Release Notes

## Bug Fixes

### Import Declarations

Usus erroneously considered import declarations to be relevant for the ACD calculation. It also accidentally attributed them to the wrong type. Now import declarations are no longer considered for the ACD because they are not part of any class.

### Inner Types

In ACD calculation, there was a bug regarding inner types: A reference to a type that appeared in an outer type after an inner type had been declared would erroneously get connected to the inner type. Now the connection is established with the outer type.

## Feedback

Please let us know what you think. If anything is not working in a way you'd expect, feel free to open an issue on GitHub: https://github.com/usus/usus-plugins/issues

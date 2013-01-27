# Usus 0.7.2 Release Notes


## New Features



## Removed Features



## Bug Fixes

### Method Length

The Method Length metric did not count single statement children of composite statements. I.e.
```
while( true )
   i++;
```
was counted as one statement, whereas   
```
while( true ){
   i++;
} 
```
was counted as two statements.
Now, both variants are counted as two statements.

## Known Problems

### ACD Calculation after the Addition or Removal of a Project

When a project is removed or added, the ACD references may not be established properly. Therefore, after the addition or removal of a project one should always perform a refresh (by pressing the Cockpit icon showing two circular arrows).


## Feedback

Please let us know what you think. If anything is not working in a way you'd expect, feel free to open an issue on GitHub: https://github.com/usus/usus-plugins/issues

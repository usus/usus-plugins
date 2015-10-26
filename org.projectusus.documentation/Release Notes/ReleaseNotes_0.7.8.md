# Usus 0.7.8 Release Notes

## New Features

A checkbox that allows to visualize coupling of packages by edge saturation has been added to the Usus package graph.

![highlight_strong_connections](https://cloud.githubusercontent.com/assets/9162198/9831219/06ec9534-594e-11e5-9173-4f6340c7035a.png)

Bright red indicates strong coupling, while salmon and gray edges mean lower coupling, suggesting package dependencies that could be removed to quickly break cyclic dependencies. In addition, the differing saturation helps in following edges through a cluttered graph such as shown in the above example.

## Feedback

Please let us know what you think. If anything is not working in a way you'd expect, feel free to open an issue on GitHub: <https://github.com/usus/usus-plugins/issues>

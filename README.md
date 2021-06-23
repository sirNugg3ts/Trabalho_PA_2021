# Connect4_FX - Diogo Pascoal

Este repositório contêm a minha execução do trabalho prático de Programação Avançada de 2020/21.

# Requisitos

```
- Java 15+
- JavaFX 16
```

## Configuração

É necessário adicionar a seguinte linha às VM options, alterando o path para onde o SDK do JavaFX se encontra instalado
```
--module-path /path/to/javafx/sdk --add-modules javafx.controls,javafx.fxml
```
Também é necessário adicionar a library aos modules do projeto.

## Diagrama de Estados

![Diagrama de Estados](https://github.com/sirNugg3ts/Trabalho_PA_2021/blob/1ed6a2aa0ae6c3082b3984eefe3de2ea8473de11/DiagramaMaquinaEstados.png?raw=true)

## Diagrama de Relações de classes

![Diagrama de Relações](https://github.com/sirNugg3ts/Trabalho_PA_2021/blob/main/DiagramaRelacoes.png?raw=true)

## Doações
Aceito Ko-Fi 😝
Use button Sponsor 😊

## License
### GNU General Public License v3.0

## Known Bugs

 - Garbage Collector não limpa antigos painéis devido a não ter sido removido o propertyChangeListener.

[![Coverage Status](https://coveralls.io/repos/github/Involute1/de.htwg.msi.Go/badge.svg?branch=master)](https://coveralls.io/github/Involute1/de.htwg.msi.Go?branch=master)
# GO
## Spielregeln
Alle genutzten Spielregeln
### Setzregeln
* Ein Zug besteht darin, einen Stein der eigenen Farbe auf einen leeren Schnittpunkt zweier Linien zu setzen. Zwei direkt benachbarte Steine einer Farbe nennt man verbunden. Verbindungen können auch länger sein, man spricht daher von Ketten. Über eine Linie benachbarte leere Felder nennt man Freiheiten. Verbundene Ketten teilen sich ihre Freiheiten.
* Selbstmordverbot:
  * Kein Stein darf so gesetzt werden, dass eine eigene Kette ohne Freiheiten entsteht, außer es werden dabei andere Steine geschlagen, wodurch neue Freiheiten entstehen.
  * Steine gelten als geschlagen, wenn ihre Kette keine Freiheiten mehr hat. Sie werden am Ende des Zuges vom Brett genommen.
* Ko-Regel wird nicht beachtet
### Spielende und Bewertung
* Ein Spiel endet, wenn ein Spieler passt (also keinen Stein legen möchte) und dann sogleich der andere Spieler ebenfalls passt. Daraufhin folgt die Spielbewertung.
* Es wird die Steinbewertung angewandt, wobei die Anzahl der Steine je Spielfarbe gezählt wird. Die höhere Anzahl gewinnt und ein unentschieden ist möglich.
### SGF-Files (Smart Game Format)
* In einem SGF-File werden die Daten eines GO-Spiels dokumentiert. Die Semantik der einzelnen Elemente ist in folgender Ressource zu finden:
* https://senseis.xmp.net/?SmartGameFormat
store='src/me/ghost/'

javac -cp lib/jsfml.jar:. $store/*.java $store/map/*.java $store/data/*.java $store/character/*.java $store/battle/*.java $store/battle/dodge/*.java
#javadoc -d ./docs -cp lib/jsfml.jar:. $store/*.java $store/map/*.java $store/data/*.java $store/character/*.java $store/battle/*.java $store/battle/dodge/*.java

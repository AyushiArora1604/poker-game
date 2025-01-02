set -e

echo "Compiling the project..."
javac -d out -sourcepath src $(find src -name "*.java")


echo "Starting the Poker Game..."
java -cp out PokerGame
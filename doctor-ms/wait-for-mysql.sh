#!/bin/sh

echo "⏳ Attente de MySQL (mysql:3306)..."

while ! nc -z mysql 3306; do
  echo "🔁 MySQL pas encore prêt... nouvelle tentative dans 2s"
  sleep 2
done

echo "✅ MySQL est prêt, démarrage de l'application..."
exec java -jar app.jar

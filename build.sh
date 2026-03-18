#!/bin/bash
set -e

REPO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
OUTPUT_DIR="$REPO_DIR/omods"

MODULES=(
  "isanteplus-openmrs_15Dec2025:isanteplus"
  "openmrs-isanteplusreports-module_13Janv2026:isanteplusreports"
  "registration:registration"
)

mkdir -p "$OUTPUT_DIR"

for entry in "${MODULES[@]}"; do
  folder="${entry%%:*}"
  artifact="${entry##*:}"

  echo ""
  echo "==> Building $artifact..."
  cd "$REPO_DIR/$folder"
  mvn clean package -DskipTests

  omod=$(find "$REPO_DIR/$folder/omod/target" -name "${artifact}-*.omod" | sort -V | tail -1)
  cp "$omod" "$OUTPUT_DIR/"
  echo "    → $OUTPUT_DIR/$(basename "$omod")"
done

echo ""
echo "All omods are in: $OUTPUT_DIR"
ls "$OUTPUT_DIR"

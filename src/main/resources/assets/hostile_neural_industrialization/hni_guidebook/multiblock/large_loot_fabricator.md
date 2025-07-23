---
navigation:
  title: "Large Loot Fabricator"
  icon: "hostile_neural_industrialization:large_loot_fabricator"
  position: 1
  parent: hostile_neural_industrialization:multiblock.md
item_ids:
  - hostile_neural_industrialization:large_loot_fabricator
---

# Large Loot Fabricator

<GameScene zoom="2" interactive={true} fullWidth={true}>
    <MultiblockShape controller="hostile_neural_industrialization:large_loot_fabricator" />
</GameScene>

The ultimate Loot Fabricator. Unlike its previous forms, it'll consume a single prediction and generate a batch of all possible loots at once.

Of course, this comes at the cost of lots of energy (Simulation Cost / 10 per tick) and snail-paced (7 min) fabrication. **Upgrades are required to fabricate loot for any prediction that requires more than 128 EU/tick**.

<Recipe id="hostile_neural_industrialization:machine/large_loot_fabricator" />

Note that some predictions, like Sheep's, have a large amount of possible outcomes, meaning you'll need many output hatches to fabricate loot from them. Remember that, like any other multiblock machine, LLFs will halt if there aren't enough hatch slots for the whole output!






---
navigation:
  title: "Câmara de Simulação Grande"
  icon: "hostile_neural_industrialization:large_simulation_chamber"
  position: 0
  parent: hostile_neural_industrialization:multiblock.md
item_ids:
  - hostile_neural_industrialization:large_simulation_chamber
---

# Câmara de Simulação Grande

<GameScene zoom="2" interactive={true} fullWidth={true}>
    <MultiblockShape controller="hostile_neural_industrialization:large_simulation_chamber" />
</GameScene>

Além de todas as particularidades da [Câmara de Simulação Elétrica](../single_block/electric_sim_chamber.md), ela também tem mais outras:

§2§l+ §r§aWQuando a sequência tiver sucesso, §l4 §r§apredições serão geradas de uma vez

§2§l+ §r§aColeta §l2 §r§adados para o modelo por sequência

§4§l- §r§cConsome §l8 §r§cMatrizes de Predição por sequência

Em suma, ela é capaz de extrair mais predições por sequência. É recomendado simular apenas modelos no nível §d§lSuperior §rou maior, já que você desperdiçaria muitas Matriz de Predição e energia por pouca recompensa no caso contrário.

<Recipe id="hostile_neural_industrialization:machine/large_simulation_chamber" />
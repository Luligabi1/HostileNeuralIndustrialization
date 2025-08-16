---
navigation:
  title: "Большая симуляционная камера"
  icon: "hostile_neural_industrialization:large_simulation_chamber"
  position: 0
  parent: hostile_neural_industrialization:multiblock.md
item_ids:
  - hostile_neural_industrialization:large_simulation_chamber
---

# Большая симуляционная камера
###### *Машина массового прогнозирования… или нет? Всё возможно.*

<GameScene zoom="2" interactive={true} fullWidth={true}>
    <MultiblockShape controller="hostile_neural_industrialization:large_simulation_chamber" />
</GameScene>

Помимо всех особенностей [Электрической симуляционной камеры](../single_block/electric_sim_chamber.md), добавляются новые:

§2§l+ §r§aПри успешной последовательности генерирует §l4 §r§предвычисления сразу  
§2§l+ §r§aСобирает §l2 §r§aединицы данных за последовательность  
§4§l− §r§cТратит §l8 §r§cматриц предвычислений за последовательность

Итог: больше предвычислений за меньшее время. Рекомендуется запускать на ранге **Высший** или выше - иначе вы потратите кучу энергии и матриц впустую.

<Recipe id="hostile_neural_industrialization:machine/large_simulation_chamber" />
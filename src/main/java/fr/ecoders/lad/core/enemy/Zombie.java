package fr.ecoders.lad.core.enemy;

public sealed interface Zombie {
  
  double lifeAmount();
  
  // Fast moving zombie
  record Runner() implements Zombie {
    @Override
    public double lifeAmount() {
      return 5;
    }
  }
  // Very radioactive zombie
  record Alchemist() implements Zombie {
    @Override
    public double lifeAmount() {
      return 8;
    }
  }
  // Tanky zombie
  record FatFuck() implements Zombie {
    @Override
    public double lifeAmount() {
      return 50;
    }
  }
  // Extremely tanky zombie
  record Behemoth() implements Zombie {
    @Override
    public double lifeAmount() {
      return 150;
    }
  }
}

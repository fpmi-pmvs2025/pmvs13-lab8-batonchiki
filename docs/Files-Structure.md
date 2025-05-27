# Структура файлов приложения

## Структура директорий

```
app/
├── src/
│   └── main/
│       ├── java/com/example/pharmacyapp/
│       │   ├── data/
│       │   │   ├── local/
│       │   │   │   ├── AppDatabase.kt
│       │   │   │   ├── dao/
│       │   │   │   └── entities/
│       │   │   ├── remote/
│       │   │   │   ├── ApiService.kt
│       │   │   │   └── models/
│       │   │   └── repository/
│       │   │       ├── ProductRepository.kt
│       │   │       └── ProductRepositoryImpl.kt
│       │   ├── ui/
│       │   │   ├── screens/
│       │   │   │   ├── catalog/
│       │   │   │   ├── details/
│       │   │   │   ├── favorites/
│       │   │   │   └── home/
│       │   │   ├── theme/
│       │   │   │   └── Theme.kt
│       │   │   ├── viewmodel/
│       │   │   └── MainActivity.kt
│       │   └── PharmacyApplication.kt
│       └── res/
└── build.gradle.kts
```

## Описание основных компонентов

### 1. Слой приложения
- **PharmacyApplication.kt**: Основной класс приложения, инициализирует ключевые компоненты и зависимости

### 2. Слой данных
- **Локальное хранилище**
  - `AppDatabase.kt`: Конфигурация базы данных Room
  - `dao/`: Объекты доступа к данным
  - `entities/`: Классы сущностей базы данных
  
- **Удаленное API**
  - `ApiService.kt`: Интерфейс Retrofit для работы с API
  - `models/`: Модели данных для API
  
- **Репозиторий**
  - `ProductRepository.kt`: Интерфейс репозитория
  - `ProductRepositoryImpl.kt`: Реализация репозитория

### 3. Слой UI
- **Экраны**
  - `catalog/`: Экран каталога товаров
  - `details/`: Экран деталей товара
  - `favorites/`: Экран избранных товаров
  - `home/`: Главный экран с акциями
  
- **ViewModels**
  - Содержит ViewModels для каждого экрана
  - Управляет бизнес-логикой и состоянием
  
- **Тема**
  - `Theme.kt`: Настройки темы Material3

### 4. Ресурсы
- Содержит макеты, изображения и другие ресурсы

## Конфигурация сборки
- **build.gradle.kts**: Основной файл конфигурации сборки
- Определяет зависимости и настройки сборки
- Настраивает версии Android SDK 
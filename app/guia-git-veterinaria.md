# 🐾 Guía de trabajo con Git - Proyecto Veterinaria API

¡Hola equipo! Esta guía les ayudará a trabajar con Git desde cero. Sigan los pasos en orden y no se salten nada. Si tienen dudas, pregunten en el grupo.

## 📥 1. Clonar el repositorio (solo la primera vez)

Abran una terminal (puede ser la de su editor de código o la de su sistema operativo) y ejecuten:

```
git clone https://github.com/DaProjett/clinica-veterinaria-api.git
```

Luego entren a la carpeta del proyecto:

```
cd clinica-veterinaria-api
```

## 🔧 2. Configurar su nombre y correo (solo la primera vez)

Esto es obligatorio para que el profesor vea quién hizo cada cambio.

```
git config user.name "Su Nombre Completo"
git config user.email "su.correo@itm.edu.co"
```

Ejemplo real:

```
git config user.name "Daniel Carmona Tabares"
git config user.email "daniel.carmona@itm.edu.co"
```

## 🌿 3. Moverse a la rama develop y actualizarla

Siempre que empiecen a trabajar, hagan esto primero:

```
git checkout develop
git pull origin develop
```

Esto les traerá lo último que hayan subido sus compañeros.

## 🌱 4. Crear su rama personal (solo una vez)

Cada persona tendrá una sola rama para toda su parte del proyecto. Elijan el comando que corresponde a su nombre:

| Persona | Responsabilidad | Comando para crear su rama |
|---------|----------------|----------------------------|
| Daniel | Carpeta model | `git checkout -b feature/model-entities` |
| Jeferson | Carpeta controller | `git checkout -b feature/rest-controllers` |
| Juan José | Carpeta service | `git checkout -b feature/business-services` |
| Nicole | Carpeta dao y archivos resources | `git checkout -b feature/database-dao` |
| Andrea | Carpeta config y util | `git checkout -b feature/config-swagger` |

Ejemplo real para Daniel:

```
git checkout -b feature/model-entities
```

Después de crear su rama, súbanla a GitHub:

```
git push -u origin feature/model-entities   # cambien el nombre según su rama
```

## ✏️ 5. Ciclo diario de trabajo (cada vez que programen)

### 📌 5.1 Antes de empezar a escribir código

```
git checkout feature/su-rama          # se ubican en su rama
git pull origin develop               # traen lo nuevo de los demás
```

### 📌 5.2 Escribir código...

Trabajen normalmente en su editor. Creen o modifiquen los archivos que les corresponden.

### 📌 5.3 Guardar los cambios (commit)

Cuando terminen una pequeña parte (por ejemplo, agregar un atributo a una clase), hagan:

```
git add .
git commit -m "Mensaje corto que explique qué hicieron"
```

Ejemplos de mensajes de commit reales:

- "Agrega atributos básicos a clase Dueno"
- "Crea método guardar() en DuenoDao"
- "Corrige nombre de columna en consulta SQL"

### 📌 5.4 Subir los cambios a GitHub

```
git push origin feature/su-rama
```

Repitan los pasos 5.2 a 5.4 cada vez que terminen una tarea pequeña. No esperen a tener todo el CRUD completo para subir.

## 🔀 6. Cuando terminen toda su parte (Pull Request)

Una vez que hayan terminado todos los archivos de su carpeta, deben solicitar que su código se integre a la rama develop. Esto se hace mediante un Pull Request en GitHub.

**Pasos en GitHub:**

1. Entren a https://github.com/DaProjett/clinica-veterinaria-api
2. Hagan clic en la pestaña "Pull requests" (arriba)
3. Botón verde "New pull request"
4. Configurar:
   - base: develop
   - compare: feature/su-rama (la rama de ustedes)
5. Revisen que los cambios mostrados sean los correctos
6. Clic en "Create pull request"
7. Título sugerido: [Nombre] - Capa [model/controller/etc] lista para revisión
   - Ejemplo: Daniel - Capa model lista para revisión
8. En la parte derecha, en Reviewers, seleccionen a Andrea (ella será la revisora principal)
9. Clic en "Create pull request"

✅ ¡Listo! Andrea revisará su código. Si hay que corregir algo, ustedes hacen el cambio en su rama, hacen commit y push, y el Pull Request se actualiza solo. Cuando todo esté bien, Andrea aprobará y se unirá a develop.

## 🧹 Comandos extra útiles

| Comando | ¿Qué hace? |
|---------|------------|
| `git status` | Muestra qué archivos han cambiado |
| `git log --oneline -5` | Muestra los últimos 5 commits |
| `git branch` | Muestra en qué rama estás |

## ⚠️ Reglas de oro (leer con atención)

- ❌ **NUNCA** trabajen directamente en main o develop. Siempre en su rama feature/....
- ❌ **NUNCA** hagan `git push` a main o develop. Solo a su propia rama.
- ✅ **SIEMPRE** hagan `git pull origin develop` antes de empezar a programar cada día.
- ✅ **SIEMPRE** pongan mensajes de commit que digan qué hicieron (nada de "cambios" o "varios").
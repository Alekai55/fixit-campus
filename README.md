# FixIt Campus — Proyecto

FixIt Campus es una plataforma de gestión de incidencias para un campus educativo. Permite reportar, hacer seguimiento y resolver averías e incidencias de mantenimiento de forma organizada.

> **Proyecto en desarrollo.** Lo que se describe aquí corresponde al estado al cierre de la primera evaluación. Quedan muchas funcionalidades por implementar y aspectos por mejorar en futuras iteraciones.

---

## Estado actual — Primera evaluación

Para esta primera evaluación se han desarrollado dos módulos independientes que sientan las bases del sistema:

### Backend Java (`backend/`)

Módulo desarrollado en Java que integra **PSP** (programación concurrente con hilos) y **Acceso a Datos** (Excel, BBDD MySQL y Word) en un único punto de entrada.

- Genera un Excel con los datos de las 9 tablas de la BBDD
- Lee ese Excel e inserta los datos en MySQL 
- Genera un informe Word con el resumen de las tablas
- Todo el proceso se ejecuta con concurrencia (ExecutorService, 3 fases) y queda registrado en logs

Consulta [`backend/README.md`](backend/README.md) para instrucciones de ejecución y detalle de los logs.

### Analytics Python (`analytics/`)

Módulo desarrollado en Python que lee un fichero JSON con incidencias y genera gráficos de análisis visual:

- Incidencias abiertas por espacio
- Media de días hasta resolución por categoría
- Distribución de incidencias por estado

El código cumple con los estándares PEP8 validados con flake8.

Consulta [`analytics/README.md`](analytics/README.md) para instrucciones de ejecución.

---

## Mejoras futuras

Entre las funcionalidades pendientes para próximas evaluaciones:

- Aplicación Android para que los usuarios reporten y consulten incidencias desde el móvil
- API REST que conecte el backend con la app y con el módulo de analytics
- Autenticación de usuarios con roles (administrador, técnico, usuario)
- Panel de administración web
- Integración de los datos reales de la BBDD en los gráficos de analytics
- Notificaciones automáticas al técnico asignado

---

## Estructura del repositorio

```
fixit-campus/
├── backend/      — Java: PSP + Acceso a Datos
├── analytics/    — Python: análisis y gráficos
└── android-app/  — App Android (en desarrollo)
```

---

*Desarrollado con mucho esfuerzo y pasión por el equipo de ADROMI Tech.*

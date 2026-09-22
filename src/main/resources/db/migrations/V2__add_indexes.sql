-- Soporta: "dame los modelos de esta marca" (MODEL.MAKE_ID -> MAKE.ID)
CREATE INDEX IX_MMT_MODEL_MAKE_ID ON MMT_MODEL (MAKE_ID);

-- Soporta: "dame los tipos/motores de este modelo" (TYPE.MODEL_ID -> MODEL.ID)
CREATE INDEX IX_MMT_TYPE_MODEL_ID ON MMT_TYPE (MODEL_ID);

-- Soporta filtro por categoría dentro de una marca (ej. solo SUVs de Toyota)
CREATE INDEX IX_MMT_MODEL_MAKE_CATEGORY ON MMT_MODEL (MAKE_ID, CATEGORY);

-- Soporta selección directa por año dentro de un modelo (patrón YMME clásico)
CREATE INDEX IX_MMT_TYPE_MODEL_YEAR ON MMT_TYPE (MODEL_ID, BUILD_YEAR);

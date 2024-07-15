-- Primero, corregimos el ALTER TABLE
ALTER TABLE usuarios
ALTER COLUMN active SET DEFAULT 1;

-- Trigger para incrementar el conteo de respuestas en topicos después de una inserción en answers
CREATE OR REPLACE FUNCTION trg_increment_qty_answers_topico()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE topicos
    SET answers = answers + 1
    WHERE id = NEW.topico_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_increment_qty_answers_topico
AFTER INSERT ON answers
FOR EACH ROW
EXECUTE FUNCTION trg_increment_qty_answers_topico();

-- Trigger para decrementar el conteo de respuestas en topicos después de una eliminación en answers
CREATE OR REPLACE FUNCTION trg_decrease_qty_answers_topico()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE topicos
    SET answers = answers - 1
    WHERE id = OLD.topico_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_decrease_qty_answers_topico
AFTER DELETE ON answers
FOR EACH ROW
EXECUTE FUNCTION trg_decrease_qty_answers_topico();


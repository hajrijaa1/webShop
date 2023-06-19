CREATE SCHEMA dbproizvodi_28;
USE dbproizvodi_28;

INSERT INTO 
	kategorija(naziv)
VALUES
	('Dzemperi'),
	('Farmerice'),
    ('Jakne'),
	('Majice'),
    ('Patike');
    
INSERT INTO 
	proizvod(naziv, cijena, kategorija_id)
VALUES
	('Dugi sivi džember', 25, 1),
    ('Dugi džemper s mašnom ', 40, 1),
    ('Široki rukav džemper', 30, 1),
    ('Bež kraći džemper', 40, 1),
    ('Slim fit', 60, 2),
	('Mom jeans', 45, 2),
    ('Crne uske', 30, 2),
    ('Trausers', 25, 2),
    ('Visoki struk', 40, 2);
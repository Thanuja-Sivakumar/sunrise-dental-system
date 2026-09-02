-- Seed data for Sunrise Dental Clinic (MySQL syntax)
-- Password for both users is "password123" (BCrypt-hashed below)
-- INSERT ... ON DUPLICATE KEY UPDATE is MySQL's equivalent of H2's MERGE INTO:
-- it inserts the row on first run, and safely does nothing harmful on later
-- app restarts instead of throwing a duplicate-key error.

INSERT INTO users (id, username, password, role, full_name) VALUES
  (1, 'admin', '$2b$10$2leWS2XuD83OMcnU.LyQUOlCP05YgNkihv0yOJBF84xpRpo.ZrE/i', 'ADMIN', 'System Administrator'),
  (2, 'reception', '$2b$10$2leWS2XuD83OMcnU.LyQUOlCP05YgNkihv0yOJBF84xpRpo.ZrE/i', 'STAFF', 'Front Desk Staff')
ON DUPLICATE KEY UPDATE username = VALUES(username);

INSERT INTO dentists (id, name, specialization) VALUES
  (1, 'Dr. Nimal Perera', 'General Dentistry'),
  (2, 'Dr. Ishara Fernando', 'Orthodontics'),
  (3, 'Dr. Kavindi Silva', 'Oral Surgery')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO treatment_types (id, name, cost, consultation_fee) VALUES
  (1, 'Dental Checkup', 1500.00, 1000.00),
  (2, 'Teeth Cleaning (Scaling)', 3500.00, 1000.00),
  (3, 'Tooth Extraction', 5000.00, 1000.00),
  (4, 'Root Canal Treatment', 15000.00, 1500.00),
  (5, 'Dental Filling', 4000.00, 1000.00),
  (6, 'Braces Consultation', 2500.00, 1500.00),
  (7, 'Emergency Treatment', 8000.00, 2000.00)
ON DUPLICATE KEY UPDATE name = VALUES(name);

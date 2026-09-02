package com.sunrise.dental.model;

/**
 * Authorization roles. Only authorized staff (STAFF or ADMIN) may use the
 * system, satisfying the "only authorized staff can use the system" requirement.
 */
public enum Role {
    ADMIN,
    STAFF
}

package io.github.ufedarovich.breadcrumb;

/**
 * Listener interface for receiving breadcrumb items click event.
 *
 * @author Uladzimir Fedarovich
 * @since 12/18/2015
 * @see BreadcrumbItem
 */
public interface BreadcrumbListener<T> {

    /**
     * Invoced, when action happened
     * @param data information, related to the item, which fired the action.
     */
    void actionPerformed(T data);
}

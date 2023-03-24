package com.smarsh.dataengineering.creator.digxml.builder;

import com.smarsh.dataengineering.conversionsdk.builder.AbstractBuilder;
import com.smarsh.dataengineering.model.digxml.UserInfo.Affiliation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AffiliationBuilder implements AbstractBuilder<AffiliationBuilder, Affiliation> {

    @Nullable
    private String employeeId;
    @Nullable
    private String title;
    @Nullable
    private String department;
    @Nullable
    private String division;
    @Nullable
    private String company;
    @Nullable
    private String building;

    @Override
    public Affiliation build() {
        var affiliation = new Affiliation();

        Optional.ofNullable(employeeId).ifPresent(affiliation::setEmployeeId);
        Optional.ofNullable(title).ifPresent(affiliation::setTitle);
        Optional.ofNullable(department).ifPresent(affiliation::setDepartment);
        Optional.ofNullable(division).ifPresent(affiliation::setDivision);
        Optional.ofNullable(company).ifPresent(affiliation::setCompany);
        Optional.ofNullable(building).ifPresent(affiliation::setBuilding);

        return affiliation;
    }

    public void setEmployeeId(@NotNull String employeeId) {
        this.employeeId = employeeId;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public void setDepartment(@NotNull String department) {
        this.department = department;
    }

    public void setDivision(@NotNull String division) {
        this.division = division;
    }

    public void setCompany(@NotNull String company) {
        this.company = company;
    }

    public void setBuilding(@NotNull String building) {
        this.building = building;
    }
}

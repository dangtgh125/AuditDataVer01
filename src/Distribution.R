# Create a sequence of numbers between 1 and 200 incrementing by 1.
	x <- seq(1, 200, by = 1)
	# Choose the mean as 2.5 and standard deviation as 0.5.
	y <- dnorm(x, mean = 133.3053979352059, sd = 9.132502939456678)
	print(y)
	write.csv(y,"1_output.csv", row.names = FALSE)